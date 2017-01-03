package com.niit.hive.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="HV_USER_CREDENTIAL")
@Component
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
//@JsonAutoDetect
public class UserCredential{

	private String username;
	
	private String password;
	
	private String role;
	
	@Column(insertable=false)
	private String status;
	
	public UserCredential() {
		//super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
