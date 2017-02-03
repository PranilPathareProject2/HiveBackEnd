package com.niit.hive.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="HV_CHAT_FORUM_COMMENT")
@Component
public class ChatForumComment extends BaseDomain {

	@Id
	private String chat_forum_comment_id;
	
	private String chat_forum_id;
	
	private String comment_by;

	private String chat_forum_comment;
	
	@Column(insertable = false, updatable = false)
	private Date comment_date;

	public String getChat_forum_comment_id() {
		return chat_forum_comment_id;
	}

	public void setChat_forum_comment_id(String chat_forum_comment_id) {
		this.chat_forum_comment_id = chat_forum_comment_id;
	}

	public String getChat_forum_id() {
		return chat_forum_id;
	}

	public void setChat_forum_id(String chat_forum_id) {
		this.chat_forum_id = chat_forum_id;
	}

	public String getComment_by() {
		return comment_by;
	}

	public void setComment_by(String comment_by) {
		this.comment_by = comment_by;
	}

	public String getChat_forum_comment() {
		return chat_forum_comment;
	}

	public void setChat_forum_comment(String chat_forum_comment) {
		this.chat_forum_comment = chat_forum_comment;
	}

	public Date getComment_date() {
		return comment_date;
	}

	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}
}
