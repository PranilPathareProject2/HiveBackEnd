package com.niit.hive.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.niit.dao.UserDAO;
import com.niit.hive.service.UserService;
import com.niit.model.User;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userdao;
	
	@Override
	public void addUser(User user) {
		userdao.addUser(user);
	}

	@Override
	public void deleteUser(String username) {
		userdao.deleteUser(username);
	}

	@Override
	public void updateUser(String username) {
		userdao.updateUser(username);
	}

}
