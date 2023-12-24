package com.moviebookingapp.controller;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;

import com.moviebookingapp.Service.UserService;
import com.moviebookingapp.dto.ForgotPassword;
import com.moviebookingapp.exception.PasswordMismatchException;
import com.moviebookingapp.exception.UserNotExistException;
import com.moviebookingapp.models.JwtRequest;
import com.moviebookingapp.models.User;

@SpringBootTest
class UserControllerTest {

	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;
	@Mock
	User user;
	
	@Mock
	JwtRequest jwtRequest;
	
	@BeforeEach
	void setUp() {
		user = new User();
		user.setUserName("nehal123");
		user.setEmail("nehal@ghn.com");
		user.setFirstName("nehal");
		user.setLastName("ahmad");
		user.setUserPassword("123");
		user.setConfirmPassword("123");
		user.setContactNumber("9123456789");
		jwtRequest.setUserName("priya");
		jwtRequest.setUserPassword("priya123");		
	}
	
	@Test
	void registerUserTest() throws Exception {
		Mockito.when(userService.registerUser(user)).thenReturn(user);
		assertEquals(new ResponseEntity<User>(user, HttpStatus.CREATED), userController.registerNewUser(user));
	}

	
	  @Test void loginTest() throws Exception {
	  when(userService.authenticate(user.getUserName(),user.getUserPassword())).
	  thenReturn(true); assertEquals(new ResponseEntity<>(true,HttpStatus.OK),
	  userController.createJwtToken(jwtRequest)); }
	 
	
	
	
	@Test
	void forgotPasswordTest() throws PasswordMismatchException, UserNotExistException {
		ForgotPassword newPassword = new ForgotPassword("12345","12345");
		when(userService.updatePassword(user.getUserName(),newPassword)).thenReturn(null);
		assertEquals(new ResponseEntity<String>("Password updated successfully!!",HttpStatus.CREATED), 
				userController.forgotPassword("nehal123", new ForgotPassword("12345","12345")));
		
	}
}
