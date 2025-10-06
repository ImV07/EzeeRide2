package com.project.dto;

public class PasswordUpdateDTO {

	private String email;
	private String newRawPassword;
	
	public PasswordUpdateDTO() {}
	public PasswordUpdateDTO(String email, String newRawPassword) {
		super();
		this.email = email;
		this.newRawPassword = newRawPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNewRawPassword() {
		return newRawPassword;
	}
	public void setNewRawPassword(String newRawPassword) {
		this.newRawPassword = newRawPassword;
	}
	
	
	
	
}
