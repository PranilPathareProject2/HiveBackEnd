package com.niit.hive.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="HV_JOB_APPLIED")
@Component
public class JobApplied {

	@Id
	private String job_applied_id;
	
	private String job_id;
	
	private String username;
	
	@Column(insertable=false)
	private Date applied_date;
	
	private String rejection_reason;
	
	@Column(insertable=false)
	private String status;

	public String getJob_applied_id() {
		return job_applied_id;
	}

	public void setJob_applied_id(String job_applied_id) {
		this.job_applied_id = job_applied_id;
	}

	public String getJob_id() {
		return job_id;
	}

	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getApplied_date() {
		return applied_date;
	}

	public void setApplied_date(Date applied_date) {
		this.applied_date = applied_date;
	}

	public String getRejection_reason() {
		return rejection_reason;
	}

	public void setRejection_reason(String rejection_reason) {
		this.rejection_reason = rejection_reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
