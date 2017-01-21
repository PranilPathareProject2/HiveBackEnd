package com.niit.hive.model;

import java.util.Date;

public class OutputMessage extends Message{

	private Date message_date;
	
	//Change the parameter to Message message, and set the this.message_date as message.getMessage() if Not working properly
	public OutputMessage(Message message, Date message_date) {
		super(message.getMessage(), message.getMessage_id(), message.getFriend_id());
		this.message_date = message_date;
		
		//Switch to this if not working properly
		//this.message_date = new Date();
	}

	public Date getMessage_date() {
		return message_date;
	}

	public void setMessage_date(Date message_date) {
		this.message_date = message_date;
	}
}
