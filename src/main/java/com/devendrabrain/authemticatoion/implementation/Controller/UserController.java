package com.devendrabrain.authemticatoion.implementation.Controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devendrabrain.authemticatoion.implementation.entity.TokenCustomData;
import com.devendrabrain.authemticatoion.implementation.entity.TokenData;
import com.devendrabrain.authemticatoion.implementation.entity.User;
import com.devendrabrain.authemticatoion.implementation.util.DateUtils;
import com.devendrabrain.authemticatoion.implementation.util.JWTUtils;

@RestController
public class UserController {

	private final String secret = "secret";

	@PostMapping("/user/authenticate")
	public User authenticateUser(@RequestBody User user) {

		// Make user validation call // if validated then create tokenm
		TokenData data = new TokenData();
		setTokenDateInfo(data, user);
		String token = JWTUtils.createToken(data, secret);
		user.setToken(token);
		return user;
	}

	@PostMapping("/user/verfiy")
	public String verfiyToken(@RequestBody User user) {
		boolean userRefreshed = JWTUtils.verifyToken(user.getToken(), secret);
		if(userRefreshed) {
			return "User is Valid ..!!";
		}
		return "UnAuthorized user ..!!";
	}

	@PostMapping("/user/refresh")
	public User refreshToken(@RequestBody User user) {
		
		boolean userRefreshed = JWTUtils.verifyToken(user.getToken(), secret);
		if(userRefreshed) {
			String userRefreshedToken = JWTUtils.refreshToken(user.getToken(), secret, 300, UUID.randomUUID().toString());
			user.setToken(userRefreshedToken);
		}else {
			user.setCustomErrorData("Access Token Expired,,, Can not be refreshed ..!!");
		}
		
		return user;
	}

	private void setTokenDateInfo(TokenData data, User user) {

		TokenCustomData customData = new TokenCustomData();

		customData.setEmployeeDepartment(user.getUsername() + " - Department " + UUID.randomUUID().toString());
		customData.setLastSignedDate("10-10-2021");
		customData.setPassword(user.getPassword());
		customData.setUserId(UUID.randomUUID().toString());
		customData.setUsername(user.getUsername());
		data.setTokenCustomData(customData);

		data.setAud("Student");
		data.setSub(user.getUsername());
		data.setIat(new Date());
		data.setExp(DateUtils.addSeconds(new Date(), 300));
		data.setIss("iss");
		data.setJti(UUID.randomUUID().toString());
	}
}
