package com.niit.hive.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="HV_FRIEND")
@Component
public class Friend extends BaseDomain{

	@Id
	private String friend_id;
	
	private String friend_username;
	
	private String user_username;
	
	@Column(insertable=false)
	private String status;
	
	@Column(insertable=false)
	private String is_online;

	public String getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}

	public String getFriend_username() {
		return friend_username;
	}

	public void setFriend_username(String friend_username) {
		this.friend_username = friend_username;
	}

	public String getUser_username() {
		return user_username;
	}

	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIs_online() {
		return is_online;
	}

	public void setIs_online(String is_online) {
		this.is_online = is_online;
	}
}
