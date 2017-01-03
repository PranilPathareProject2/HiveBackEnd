package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.User;
import com.niit.hive.model.UserCredential;

public interface UserCredentialDAO {
	public boolean addUserCredential(UserCredential usercredential);
	public boolean updateUserCredential(UserCredential usercredential);
	public UserCredential getUserCredential(String username);
	public List listUserCredentials();
}
