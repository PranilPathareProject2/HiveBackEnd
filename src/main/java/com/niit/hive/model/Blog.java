package com.niit.hive.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="HV_BLOG")
@Component
public class Blog extends BaseDomain {

	@Id
	private String blog_id;
	
	private String blog_title;
	
	private String blog_description;
	
	private String created_by;
	
	@Column(insertable = false, updatable = false)
	private Date published_on;
	
	@Column(insertable = false)
	private String status;
	
	private String rejection_reason;

	public String getBlog_id() {
		return blog_id;
	}

	public void setBlog_id(String blog_id) {
		this.blog_id = blog_id;
	}

	public String getBlog_title() {
		return blog_title;
	}

	public void setBlog_title(String blog_title) {
		this.blog_title = blog_title;
	}

	public String getBlog_description() {
		return blog_description;
	}

	public void setBlog_description(String blog_description) {
		this.blog_description = blog_description;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getPublished_on() {
		return published_on;
	}

	public void setPublished_on(Date published_on) {
		this.published_on = published_on;
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
