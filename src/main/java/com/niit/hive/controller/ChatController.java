package com.niit.hive.controller;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.niit.hive.model.Message;
import com.niit.hive.model.OutputMessage;

@Controller
public class ChatController {
	
	@MessageMapping("/chat") //to send message
	@SendTo("/queue/message") //to receive message
	public OutputMessage sendReceiveMessage(Message message)
	{
		return new OutputMessage(message, new Date());
	}
}
