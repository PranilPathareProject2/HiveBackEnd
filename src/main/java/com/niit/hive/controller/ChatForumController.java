package com.niit.hive.controller;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.niit.hive.model.Message;
import com.niit.hive.model.OutputMessage;

@Controller
public class ChatForumController {

	@MessageMapping("/chatforum") //to send message
	@SendTo("/topic/message") //to receive message
	public OutputMessage sendReceiveMessage(Message message)
	{
		//System.out.println("Message:"+message.getMessage());
		return new OutputMessage(message, new Date());
	}
}
