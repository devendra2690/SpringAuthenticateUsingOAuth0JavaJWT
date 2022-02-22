package com.devendrabrain.authemticatoion.implementation.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TokenData {

	// DO NOT RENAME Field Names --Trilok
	private List<String> headers = new ArrayList<>(Arrays.asList("alg", "cty", "typ", "kid"));
	private List<String> payload = new ArrayList<>(Arrays.asList("iss", "sub", "exp", "nbf", "iat", "jti", "aud"));

	private String jti;
	private Date iat;
	private Date exp;
	private String sub;
	private String iss;
	private String aud;
	private String nbf;

	private TokenCustomData tokenCustomData;

	public TokenCustomData getTokenCustomData() {
		return tokenCustomData;
	}

	public void setTokenCustomData(TokenCustomData tokenCustomData) {
		this.tokenCustomData = tokenCustomData;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<String> getPayload() {
		return payload;
	}

	public void setPayload(List<String> payload) {
		this.payload = payload;
	}

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}

	public Date getIat() {
		return iat;
	}

	public void setIat(Date iat) {
		this.iat = iat;
	}

	public Date getExp() {
		return exp;
	}

	public void setExp(Date exp) {
		this.exp = exp;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public String getAud() {
		return aud;
	}

	public void setAud(String aud) {
		this.aud = aud;
	}

	public String getNbf() {
		return nbf;
	}

	public void setNbf(String nbf) {
		this.nbf = nbf;
	}
}
