package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.User;
import com.niit.hive.model.UserCredential;

public interface UserCredentialDAO {
	public boolean addUserCredential(UserCredential usercredential);
	public boolean updateUserCredential(UserCredential usercredential);
	public UserCredential getUserCredential(String username);
	public List listUserCredentials();
	public boolean acceptUser(String username);
	public boolean rejectUser(String username, String reason);
	public UserCredential authenticateUser(String username, String password);
}
