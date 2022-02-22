package com.devendrabrain.authemticatoion.implementation.entity;

public class User {

	private String username;
	private String password;
	private String token;
	private String customErrorData;

	public String getCustomErrorData() {
		return customErrorData;
	}

	public void setCustomErrorData(String customErrorData) {
		this.customErrorData = customErrorData;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
