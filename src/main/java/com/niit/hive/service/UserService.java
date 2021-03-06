package com.niit.hive.service;

import java.util.List;

import com.niit.hive.model.User;

public interface UserService {
	public boolean addUser(User user);
	public boolean updateUser(String username);
	public User getUser(String username);
	public List listUsers();
}
