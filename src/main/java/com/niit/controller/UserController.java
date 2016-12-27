package com.niit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.niit.dao.UserDAO;
import com.niit.model.User;

@Controller
public class UserController {

	@Autowired
	UserDAO userdao;
	
	@RequestMapping("/adduser")
	public String adduser()
	{
		User user = new User();
		user.setUsername(1);
		user.setPassword("pranil20");
		userdao.addUser(user);
		return "success";
	}
	
	@RequestMapping("/deleteuser")
	public String deleteuser()
	{
		userdao.deleteUser("Pranil");
		return "success";
	}
	
	@RequestMapping("/updateuser")
	public String updateuser()
	{
		userdao.updateUser("Pranil");
		return "success";
	}
}
