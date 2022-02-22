package com.devendrabrain.authemticatoion.implementation.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.devendrabrain.authemticatoion.implementation.entity.TokenCustomData;
import com.devendrabrain.authemticatoion.implementation.entity.TokenData;

public class JWTUtils {
	
	public static Builder setCustomClaims(TokenCustomData rsClaim, Builder builder) throws Exception {
		try {
			Map<String, String> stringClaims = RSReflectionUtils.getFieldNamesAndValuesAsString(rsClaim);
			for (Map.Entry<String, String> entry : stringClaims.entrySet()) {
				builder.withClaim(entry.getKey(), entry.getValue());
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.out.println("ClassName: " + JWTUtils.class.getSimpleName()+ e);
			throw new Exception(e);
		}
		try {
			Map<String, String[]> stringArrayClaims = RSReflectionUtils.getFieldNamesAndValuesAsArray(rsClaim);
			for (Map.Entry<String, String[]> entry : stringArrayClaims.entrySet()) {
				builder.withArrayClaim(entry.getKey(), entry.getValue());
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.out.println("ClassName: " + JWTUtils.class.getSimpleName()+e);
			throw new Exception(e);
		}

		return builder;
	}
	
	public static String createToken(TokenData rsClaim, String secret) {
	
		String token = null;
		try {
			
			Algorithm algorithm = Algorithm.HMAC512(secret);
			Builder builder = JWT.create()
					          .withIssuedAt(rsClaim.getIat())
					          .withIssuer(rsClaim.getIss())
					          .withJWTId(rsClaim.getJti())
					          .withExpiresAt(rsClaim.getExp())
					          .withSubject(rsClaim.getTokenCustomData().getUsername())
					          .withAudience(rsClaim.getAud());
			try {
			 builder = JWTUtils.setCustomClaims(rsClaim.getTokenCustomData(), builder);
			}catch (Exception e) {
				System.out.println("ClassName: " + JWTUtils.class.getSimpleName()+ e);
			}		          
			token = builder.sign(algorithm);			
		}catch (Exception e) {
			System.out.println("ClassName: " + JWTUtils.class.getSimpleName()+ e);
		}
		
		return token;
	}
	
	public static String refreshToken(String token, String secret, Integer tokenValidityInSec, String jti) {
		TokenData rsClaim = decode(token);
		rsClaim.setJti(jti);
		rsClaim.setIat(new Date());
		rsClaim.setExp(DateUtils.addSeconds(new Date(), tokenValidityInSec));
		return createToken(rsClaim, secret);
	}
	
	public static TokenData decode(String token) {
		TokenData rsClaim = new TokenData();
        TokenCustomData customData = new TokenCustomData();  
        
		DecodedJWT jwt = JWT.decode(token);
		Map<String, Claim> claims = jwt.getClaims();
		List<String> headers = rsClaim.getHeaders();
		List<String> payload = rsClaim.getPayload();
		for (String header : headers) {
			rsClaim = (TokenData) JWTUtils.setCustomClaim(rsClaim, header, jwt.getHeaderClaim(header), TokenData.class);
		}
		for (Map.Entry<String, Claim> entry : claims.entrySet()) {
			String key = entry.getKey();
			Claim claim = entry.getValue();
			if (payload.contains(key)) {
				rsClaim = (TokenData) JWTUtils.setCustomClaim(rsClaim, key, claim, TokenData.class);
			}else  {
				customData = (TokenCustomData) JWTUtils.setCustomClaim(customData, key, claim, TokenCustomData.class);
			}
		}
		
		rsClaim.setTokenCustomData(customData);
		return rsClaim;
	}
	
	public static boolean verifyToken(String token, String secret) {
		try {
			Algorithm algorithm = Algorithm.HMAC512(secret);
			JWTVerifier verifier = JWT.require(algorithm).build(); // Reusable
																	// verifier
																	// instance
			verifier.verify(token);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			System.out.println("ClassName: " + JWTUtils.class.getSimpleName()+ e);
			return false;
		} catch (JWTVerificationException e) {
			System.out.println("ClassName: " + JWTUtils.class.getSimpleName()+ e);
			// Invalid signature/claims
			return false;
		}
		catch (Exception exception) {
			System.out.println("ClassName: " + JWTUtils.class.getSimpleName()+ exception);
			// UTF-8 encoding not supported
			return false;
		}

		return true;
	}
	
	public static Object setCustomClaim(Object obj, String key, Claim claim, Class<?> cls) {
		Field string = ReflectionUtils.findField(cls, key, String.class);
		if (string != null) {
			string.setAccessible(true);
			ReflectionUtils.setField(string, obj, claim.asString());
			return obj;
		}
		Field stringArray = ReflectionUtils.findField(cls, key, String[].class);
		if (stringArray != null) {
			stringArray.setAccessible(true);
			ReflectionUtils.setField(stringArray, obj, claim.asArray(String.class));
			return obj;
		}
		Field date = ReflectionUtils.findField(cls, key, Date.class);
		if (date != null) {
			date.setAccessible(true);
			ReflectionUtils.setField(date, obj, claim.asDate());
			return obj;
		}
		return obj;
	}
}
