package com.joelkingsley.rmkcet.spas.be.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joelkingsley.rmkcet.spas.be.beans.Batch;
import com.joelkingsley.rmkcet.spas.be.beans.User;
import com.joelkingsley.rmkcet.spas.be.beans.requests.AddUserRequest;
import com.joelkingsley.rmkcet.spas.be.constants.ErrorConstants;
import com.joelkingsley.rmkcet.spas.be.delegates.UsersDelegate;
import com.joelkingsley.rmkcet.spas.be.utils.AppError;

@RestController
public class UsersController {

	UsersDelegate usersDelegate;
	
	public UsersController() {
		super();
		usersDelegate = new UsersDelegate();
	}

	@GetMapping("users")
	ResponseEntity<?> getAllUsers() {
		try {
			ArrayList<User> users = usersDelegate.getAllUsers();
			if(users == null) {
				ResponseEntity<String> responseEntity = new ResponseEntity<String>(ErrorConstants.USERS_NOT_FOUND, HttpStatus.NOT_FOUND);
				return responseEntity;
			} else {
				ResponseEntity<ArrayList<User>> responseEntity = new ResponseEntity<ArrayList<User>>(users, HttpStatus.OK);
				return responseEntity;
			}
		} catch (AppError appError) {
			appError.getException().printStackTrace();
			ResponseEntity<String> responseEntity = new ResponseEntity<String>(appError.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	@PostMapping("users")
	ResponseEntity<?> addUser(@RequestBody AddUserRequest addUserRequest) {
		 try { 
			 AddUserRequest addedUser = usersDelegate.addUser(addUserRequest); 
			 ResponseEntity<AddUserRequest> responseEntity = new ResponseEntity<AddUserRequest>(addedUser, HttpStatus.OK); 
			 return responseEntity;
	
		} catch (AppError appError) { 
			appError.getException().printStackTrace(); 
			ResponseEntity<String> responseEntity = new ResponseEntity<String>(appError.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
			return responseEntity; 
		}
	}
	
}
