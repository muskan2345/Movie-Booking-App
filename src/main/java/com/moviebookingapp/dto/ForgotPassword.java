package com.moviebookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class ForgotPassword {

	private String userPassword;
	private String confirmPassword;
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public ForgotPassword(String userPassword, String confirmPassword) {
		super();
		this.userPassword = userPassword;
		this.confirmPassword = confirmPassword;
	}
	
	
	
	
	
}
