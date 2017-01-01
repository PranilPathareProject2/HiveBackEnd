package com.niit.hive.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Users")
@Component
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@JsonAutoDetect
public class User extends BaseDomain{

	@Id
	private int username;
	
	private String password;
	
	public User() {
		//super();
	}
	
	public User(int username, String password) {
		//super();
		this.username = username;
		this.password = password;
	}

	public int getUsername() {
		return username;
	}
	public void setUsername(int username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
