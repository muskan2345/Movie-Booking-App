package com.moviebookingapp.Service;

import javax.validation.ConstraintViolationException;

import com.moviebookingapp.dto.ForgotPassword;
import com.moviebookingapp.dto.ResetPassword;
import com.moviebookingapp.exception.PasswordMismatchException;
import com.moviebookingapp.exception.UserAlredyExistException;
import com.moviebookingapp.exception.UserNotExistException;
import com.moviebookingapp.models.User;

public interface UserService {

	public User registerUser(User user) throws ConstraintViolationException, PasswordMismatchException, UserAlredyExistException;
	public boolean authenticate(String userName, String userPassword);
	public String updatePassword(String userPassword, ForgotPassword forgotPassword) throws PasswordMismatchException, UserNotExistException;
	public void initRolesAndUser() throws Exception;
	public User registerNewUser(User user) throws Exception;


	public  Boolean  resetPassword(String userName, ResetPassword resetPassword)  throws UserNotExistException;

}
