package com.moviebookingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.moviebookingapp.models.Role;
import com.moviebookingapp.serviceImpl.RoleService;
import java.util.*;



@Controller
public class RestController {
	
	 @Autowired
	 private RoleService roleservice;  
	 @PostMapping({"/createNewRole"})
	public ResponseEntity<Role> createNewRole(@RequestBody Role role) {
		 return

				new ResponseEntity<>(roleservice.createNewRole(role), HttpStatus.OK);
	}

	@GetMapping ({"/getRole"})
	public ResponseEntity<List<Role>> getAllRole(List<Role> roles){


		 return new ResponseEntity<>(roleservice.getRoles(), HttpStatus.OK);
	}


	 
	 

}
