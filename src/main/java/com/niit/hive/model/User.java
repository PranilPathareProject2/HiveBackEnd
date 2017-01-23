package com.niit.hive.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="HV_USER_DETAIL")
@Component
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
//@JsonAutoDetect
public class User extends BaseDomain{

	@Id
	private String username;
	
	private String firstname;
	private String lastname;
	private Date date_of_birth;
	private String email_id;
	private String gender;
	private String contact_no;
	private String address;
	private String state;
	private String city;
	private String pincode;
	private String experience;
	private String qualification;
	//private String reason;
	
	@Column(insertable=false)
	private String status;
	
	@Transient
	private String password;
	
	@Transient
	private String role;
	
	@Transient
	private MultipartFile multipartfile;
	
	public User() {
		//super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	/*public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}*/

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public MultipartFile getMultipartfile() {
		return multipartfile;
	}

	public void setMultipartfile(MultipartFile multipartfile) {
		this.multipartfile = multipartfile;
	}
	
}
