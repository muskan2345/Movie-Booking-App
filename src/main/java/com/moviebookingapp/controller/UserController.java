package com.moviebookingapp.controller;

import javax.annotation.PostConstruct;


import javax.validation.Valid;

import com.moviebookingapp.dto.ResetPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.Service.JwtService;
import com.moviebookingapp.Service.UserService;
import com.moviebookingapp.dto.ForgotPassword;
import com.moviebookingapp.exception.PasswordMismatchException;
import com.moviebookingapp.exception.UserAlredyExistException;
import com.moviebookingapp.exception.UserNotExistException;
//import com.moviebookingapp.kafka.Producer;
import com.moviebookingapp.models.JwtRequest;
import com.moviebookingapp.models.JwtResponse;
//import com.moviebookingapp.kafka.Producer;
import com.moviebookingapp.models.User;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class UserController {
	
	 Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@Autowired
    private JwtService jwtService;

    @PostMapping({"/login"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
	
	//@Autowired
	//Producer producer;
	
	@PostMapping({"/registerNewUser"})
	public User registerNewUser(@Valid @RequestBody User user) throws Exception {
		return userService.registerNewUser(user);
	}
	
	@PostConstruct
	public void initRolesAndUser() throws Exception {
		userService.initRolesAndUser();
	}
	
	@GetMapping({"/forAdmin"})
	@PreAuthorize("hasRole('Admin')")
	public String forAdmin() {
		return "This String is only accessable to admin";
	}
	@GetMapping({"/forUser"})
	@PreAuthorize("hasRole('User')")
	public String forUser() {
		return "This String is only accessable to user";
	}
	
	
	@PutMapping("/{userName}/forgot")
	public ResponseEntity<String> forgotPassword(@PathVariable String userName,@RequestBody ForgotPassword forgotPassword) throws PasswordMismatchException, UserNotExistException{
		userService.updatePassword(userName,forgotPassword);
		//producer.sendMessage(loginId);
		logger.info("password updated successfully");
		return new ResponseEntity<String>("Password updated successfully!!",HttpStatus.CREATED);
	}

	@PutMapping("/{userName}/reset")

	public ResponseEntity<?>   resetPassword(@PathVariable String  userName, @RequestBody ResetPassword resetPassword) throws UserNotExistException,PasswordMismatchException{


		boolean returnVal=false;
		if(resetPassword.getUserPassword().equals(resetPassword.getConfirmPassword())){
			returnVal = userService.resetPassword(userName, resetPassword);
		}
		else{
			return new ResponseEntity<>("Password is not Reset",HttpStatus.BAD_REQUEST);
		}
      if(returnVal!=true)
	  {
		  return new ResponseEntity<>( "User Not found",HttpStatus.NOT_FOUND );

	  }

	  	  return new ResponseEntity<>("Password Reset Successfully",HttpStatus.OK );


	}

	
	
	
	
	
	/*
	 * @PostMapping("/register") public ResponseEntity<User>
	 * registerUser(@Valid @RequestBody User user) throws UserAlredyExistException,
	 * PasswordMismatchException{
	 * 
	 * logger.info("register as new user"); User newUser =
	 * userService.registerUser(user); producer.sendMessage(newUser.getLoginId());
	 * logger.info("New user registered"); return new ResponseEntity<User>(newUser,
	 * HttpStatus.CREATED);
	 */
	
//}
	
	/*
	 * @GetMapping("/login/{userName}/{userPassword}") public ResponseEntity<?>
	 * login(@PathVariable String userName, @PathVariable String userPassword){
	 * boolean result=userService.authenticate(userName, userPassword);
	 * logger.info("logged in process"); return new
	 * ResponseEntity<>(result,HttpStatus.OK); }
	 */
	

}

