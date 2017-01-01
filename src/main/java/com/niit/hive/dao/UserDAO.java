package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.User;

public interface UserDAO {
	public boolean addUser(User user);
	public boolean updateUser(User user);
	public User getUser(int username);
	public List listUsers();
}
