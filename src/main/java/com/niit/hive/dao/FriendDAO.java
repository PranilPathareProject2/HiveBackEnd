package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.Friend;

public interface FriendDAO {

	public boolean addFriendRequest(Friend friend);
	public boolean updateFriendship(Friend friend);
	public Friend getFriendship(String user_username, String friend_username);
	public List listFriends(String user_username);
	public void setOnline(String user_username);
	public void setOffline(String user_username);
	public List getNewFriendRequests(String user_username);
	List getSentFriendRequests(String user_username);

}
