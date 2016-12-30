package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.User;

public interface UserDAO {
	public boolean addUser(User user);
	public boolean updateUser(int username);
	public User getUser(String username);
	public List listUsers();
}
