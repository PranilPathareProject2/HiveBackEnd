package com.niit.hive.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.hive.dao.UserDAO;
import com.niit.hive.model.User;

@RestController
public class UserController {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	User user;
	
	@RequestMapping(value="/adduser", method=RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody User user)
	{
		
		boolean status = userDAO.addUser(user);
		if(status==false)
		{
			//user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("The registration is not success");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateuser", method=RequestMethod.PUT)
	public ResponseEntity<User> updateUserInDB(@RequestBody User user)
	{
		boolean status = userDAO.updateUser(user);
		if(status==false)
		{
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("The registration is not success");
		}
		System.out.println("Status ==="+status);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/listusers", method=RequestMethod.GET)
	public ResponseEntity<List<User>> listUsers()
	{
		List<User> users = userDAO.listUsers();
		
		if(users.isEmpty())
		{
			user.setErrorCode("404");
			user.setErrorMessage("No users are available");
			users.add(user);
		}
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getuser", method=RequestMethod.GET)
	public ResponseEntity<User> getUserById(@RequestParam int id)
	{
		user = userDAO.getUser(id);
		
		if(user == null)
		{
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("The registration is not success");
		}
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
