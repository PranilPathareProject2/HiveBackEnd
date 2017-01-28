package com.niit.hive.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="HV_BLOG_COMMENT")
@Component
public class BlogComment extends BaseDomain {

	@Id
	private String blog_comment_id;
	
	private String blog_id;
	
	private String comment_by;
	
	private String blog_comment;
	
	@Column(insertable = false, updatable = false)
	private Date blog_comment_date;

	public String getBlog_comment_id() {
		return blog_comment_id;
	}

	public void setBlog_comment_id(String blog_comment_id) {
		this.blog_comment_id = blog_comment_id;
	}

	public String getBlog_id() {
		return blog_id;
	}

	public void setBlog_id(String blog_id) {
		this.blog_id = blog_id;
	}

	public String getComment_by() {
		return comment_by;
	}

	public void setComment_by(String comment_by) {
		this.comment_by = comment_by;
	}

	public String getBlog_comment() {
		return blog_comment;
	}

	public void setBlog_comment(String blog_comment) {
		this.blog_comment = blog_comment;
	}

	public Date getBlog_comment_date() {
		return blog_comment_date;
	}

	public void setBlog_comment_date(Date blog_comment_date) {
		this.blog_comment_date = blog_comment_date;
	}
}
