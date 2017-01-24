package com.niit.hive.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niit.hive.dao.FriendDAO;
import com.niit.hive.dao.UserCredentialDAO;
import com.niit.hive.dao.UserDAO;
import com.niit.hive.model.User;
import com.niit.hive.model.UserCredential;
import com.niit.hive.util.FileUpload;

@RestController
public class UserController {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	FriendDAO friendDAO;
	
	@Autowired
	User user;
	
	@Autowired
	UserCredentialDAO userCredentialDAO;
	
	@Autowired
	UserCredential userCredential;
	
	@RequestMapping(value="/adduser", method=RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody User user)
	{
		if(userDAO.getUser(user.getUsername())!=null)
		{
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("The registration is not success, username is already in use");
		}
		else
		{
			userDAO.addUser(user);
			userCredential.setUsername(user.getUsername());
			userCredential.setPassword(user.getPassword());
			userCredential.setRole(user.getRole());
			userCredentialDAO.addUserCredential(userCredential);
			user.setErrorCode("200");
			user.setErrorMessage("The registration is success");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateuser", method=RequestMethod.PUT)
	public ResponseEntity<User> updateUserInDB(@RequestBody User user)
	{
		if(userDAO.getUser(user.getUsername())==null)
		{
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Update was not a success");
		}
		else
		{
			userDAO.updateUser(user);
			user.setErrorCode("200");
			user.setErrorMessage("Update was successful");
		}
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/listusers", method=RequestMethod.GET)
	public ResponseEntity<List<User>> listUsers()
	{
		List<User> users = userCredentialDAO.listApprovedUsers();
		
		if(users.isEmpty())
		{
			user.setErrorCode("404");
			user.setErrorMessage("No users are available");
			users.add(user);
		}
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getuser/{username}", method=RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable("username") String username)
	{
		user = userDAO.getUser(username);
		
		if(user == null)
		{
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("The requested user is not found");
		}
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/manageusers", method=RequestMethod.GET)
	public ResponseEntity<List<UserCredential>> manageUsers()
	{
		List<UserCredential> users = userCredentialDAO.listUserCredentials();
		
		if(users.isEmpty())
		{
			userCredential.setErrorCode("404");
			userCredential.setErrorMessage("No users are available");
			users.add(userCredential);
		}
		
		return new ResponseEntity<List<UserCredential>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value="/acceptuser/{username}", method=RequestMethod.PUT)
	public ResponseEntity<UserCredential> acceptUsers(@PathVariable("username") String username)
	{
		if(!userCredentialDAO.acceptUser(username))
		{
			userCredential = new UserCredential();
			userCredential.setErrorCode("404");
			userCredential.setErrorMessage("Accepting was not a success");
		}
		else
		{
			userCredential.setErrorCode("200");
			userCredential.setErrorMessage("Accepting was successful");
		}
		
		return new ResponseEntity<UserCredential>(userCredential, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectuser/{username}/{reason}", method=RequestMethod.PUT)
	public ResponseEntity<UserCredential> rejectUsers(@PathVariable("username") String username, @PathVariable("reason") String reason)
	{
		if(!userCredentialDAO.rejectUser(username, reason))
		{
			userCredential = new UserCredential();
			userCredential.setErrorCode("404");
			userCredential.setErrorMessage("Rejecting was not a success");
		}
		else
		{
			userCredential.setErrorCode("200");
			userCredential.setErrorMessage("Rejecting was successful");
		}
		
		return new ResponseEntity<UserCredential>(userCredential, HttpStatus.OK);
	}
	
	@RequestMapping(value="/makeadmin/{username}", method=RequestMethod.PUT)
	public ResponseEntity<UserCredential> makeAdmin(@PathVariable("username") String username)
	{
		if(!userCredentialDAO.makeAdmin(username))
		{
			userCredential = new UserCredential();
			userCredential.setErrorCode("404");
			userCredential.setErrorMessage("Making admin was not a success");
		}
		else
		{
			userCredential.setErrorCode("200");
			userCredential.setErrorMessage("Making admin was successful");
		}
		
		return new ResponseEntity<UserCredential>(userCredential, HttpStatus.OK);
	}
	
	@RequestMapping(value="/authenticateuser", method=RequestMethod.POST)
	public ResponseEntity<UserCredential> authenticateUsers(@RequestBody UserCredential usercred, HttpSession httpSession)
	{
		UserCredential usercredobj = userCredentialDAO.authenticateUser(usercred.getUsername(), usercred.getPassword());
		
		if(usercredobj == null)
		{
			usercredobj = new UserCredential();
			usercredobj.setErrorCode("404");
			usercredobj.setErrorMessage("Invalid Credentials... Logging in was not a success");
		}
		else
		{
			usercredobj.setErrorCode("200");
			usercredobj.setErrorMessage("Successfully logged in");
			httpSession.setAttribute("loggedInUser", usercredobj.getUsername());
			httpSession.setAttribute("loggedInUserRole", usercredobj.getRole());
			userDAO.setOnline(usercredobj.getUsername());
			//friendDAO.setOnline(usercredobj.getUsername());
		}
		
		return new ResponseEntity<UserCredential>(usercredobj, HttpStatus.OK);
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public ResponseEntity<UserCredential> logoutUser(HttpSession httpSession)
	{
		String username = (String) httpSession.getAttribute("loggedInUser");
		userDAO.setOffline(username);
		//friendDAO.setOffline(username);
		
		httpSession.invalidate();
		
		userCredential.setErrorCode("200");
		userCredential.setErrorMessage("Successfully logged out...");;
		return new ResponseEntity<UserCredential>(userCredential, HttpStatus.OK);
	}
}
