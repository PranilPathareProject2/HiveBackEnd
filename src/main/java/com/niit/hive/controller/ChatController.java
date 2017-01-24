package com.niit.hive.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.niit.hive.model.Message;
import com.niit.hive.model.OutputMessage;

@Controller
public class ChatController {

	/*@Autowired
	private SimpMessagingTemplate template;

    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }*/
	
	@MessageMapping("/chat") //to send message
	@SendTo("/queue/chatmessage")//to receive message
	public OutputMessage sendReceiveMessage(Message message)
	{
		return new OutputMessage(message, new Date());
	}
}
