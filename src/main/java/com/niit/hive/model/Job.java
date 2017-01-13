package com.niit.hive.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="HV_JOB")
@Component
public class Job extends BaseDomain {

	@Id
	private String job_id;
	
	private String job_title;
	
	private String job_description;
	
	@Column(insertable=false, updatable=false)
	private Date posted_date;
	
	private String job_designation;
	
	@Column(insertable=true, updatable=true)
	private String job_salary;
	
	private String job_location;

	private String experience;
	
	@Column(insertable=false, updatable=false)
	private String status;

	public String getJob_id() {
		return job_id;
	}

	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}

	public String getJob_title() {
		return job_title;
	}

	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}

	public String getJob_description() {
		return job_description;
	}

	public void setJob_description(String job_description) {
		this.job_description = job_description;
	}

	public Date getPosted_date() {
		return posted_date;
	}

	public void setPosted_date(Date posted_date) {
		this.posted_date = posted_date;
	}

	public String getJob_designation() {
		return job_designation;
	}

	public void setJob_designation(String job_designation) {
		this.job_designation = job_designation;
	}

	public String getJob_salary() {
		return job_salary;
	}

	public void setJob_salary(String job_salary) {
		this.job_salary = job_salary;
	}

	public String getJob_location() {
		return job_location;
	}

	public void setJob_location(String job_location) {
		this.job_location = job_location;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
