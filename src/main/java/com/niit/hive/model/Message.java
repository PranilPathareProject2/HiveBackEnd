package com.niit.hive.model;

public class Message {

	private String message;
	
	private int message_id;
	
	private String friend_id;
	
	public Message() {
		
	}
	
	public Message(String message, int message_id, String friend_id) {
		this.message = message;
		this.message_id = message_id;
		this.friend_id = friend_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public String getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}
}
