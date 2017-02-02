package com.niit.hive.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="HV_CHAT_FORUM")
@Component
public class ChatForum extends BaseDomain {

	@Id
	private String chat_forum_id;
	
	private String chat_forum_name;
	
	private String created_by;
	
	@Column(insertable = false, updatable = false)
	private Date created_date;
	
	@Column(insertable = false)
	private String status;
	
	private String rejection_reason;

	public String getChat_forum_id() {
		return chat_forum_id;
	}

	public void setChat_forum_id(String chat_forum_id) {
		this.chat_forum_id = chat_forum_id;
	}

	public String getChat_forum_name() {
		return chat_forum_name;
	}

	public void setChat_forum_name(String chat_forum_name) {
		this.chat_forum_name = chat_forum_name;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRejection_reason() {
		return rejection_reason;
	}

	public void setRejection_reason(String rejection_reason) {
		this.rejection_reason = rejection_reason;
	}
}
