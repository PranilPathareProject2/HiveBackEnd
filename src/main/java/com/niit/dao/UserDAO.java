package com.niit.dao;

import com.niit.model.User;

public interface UserDAO {
	void addUser(User user);
	void deleteUser(String username);
	void updateUser(String username);
}
