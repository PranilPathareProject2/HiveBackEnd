package com.niit.hive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.niit.hive.dao.UserDAO;
import com.niit.hive.model.User;

@Controller
public class UserController {

	@Autowired
	UserDAO userDAO;
	
	@RequestMapping("/adduser")
	public String adduser()
	{
		User user = new User();
		user.setUsername(3);
		user.setPassword("pranil21");
		boolean status = userDAO.addUser(user);
		System.out.println("Status ++"+status);
		return "success";
	}
	
	@RequestMapping("/updateuser")
	public String updateuser()
	{
		boolean status = userDAO.updateUser(2);
		System.out.println("Status ++"+status);
		return "success";
	}
}
