package com.niit.hive.service;

import com.niit.model.User;

public interface UserService {
	void addUser(User user);
	void deleteUser(String username);
	void updateUser(String username);
}
