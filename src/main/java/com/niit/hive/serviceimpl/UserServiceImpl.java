package com.niit.hive.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.niit.hive.dao.UserDAO;
import com.niit.hive.service.UserService;
import com.niit.hive.model.User;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;

	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List listUsers() {
		// TODO Auto-generated method stub
		return null;
	}
}