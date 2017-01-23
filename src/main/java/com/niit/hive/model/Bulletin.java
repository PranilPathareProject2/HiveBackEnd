package com.niit.hive.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="HV_BULLETIN")
@Component
public class Bulletin {

	@Id
	private String bulletin_id;
	
	private String bulletin_title;
	
	private String bulletin_description;

	public String getBulletin_id() {
		return bulletin_id;
	}

	public void setBulletin_id(String bulletin_id) {
		this.bulletin_id = bulletin_id;
	}

	public String getBulletin_title() {
		return bulletin_title;
	}

	public void setBulletin_title(String bulletin_title) {
		this.bulletin_title = bulletin_title;
	}

	public String getBulletin_description() {
		return bulletin_description;
	}

	public void setBulletin_description(String bulletin_description) {
		this.bulletin_description = bulletin_description;
	}
}
