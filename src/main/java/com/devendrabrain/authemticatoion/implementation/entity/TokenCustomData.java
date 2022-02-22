package com.devendrabrain.authemticatoion.implementation.entity;

public class TokenCustomData {

	private String username;
	private String password;
	private String userId;
	private String lastSignedDate;
	private String employeeDepartment;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastSignedDate() {
		return lastSignedDate;
	}

	public void setLastSignedDate(String lastSignedDate) {
		this.lastSignedDate = lastSignedDate;
	}

	public String getEmployeeDepartment() {
		return employeeDepartment;
	}

	public void setEmployeeDepartment(String employeeDepartment) {
		this.employeeDepartment = employeeDepartment;
	}

}
