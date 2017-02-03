package com.niit.hive.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.niit.hive.dao.ChatForumDAO;
import com.niit.hive.model.ChatForum;
import com.niit.hive.model.ChatForumComment;
import com.niit.hive.model.Message;
import com.niit.hive.model.OutputMessage;

@Controller
public class ChatForumController {

	@Autowired
	ChatForumDAO chatForumDAO;
	
	@Autowired
	ChatForum chatForum;
	
	@Autowired
	ChatForumComment chatForumComment;
	
	@Autowired
	HttpSession session;
	
	@MessageMapping("/chatforum") //to send message
	@SendTo("/topic/message") //to receive message
	public OutputMessage sendReceiveMessage(Message message)
	{
		/*String loggedInUser = (String) session.getAttribute("loggedInUser");*/
		chatForumComment.setChat_forum_comment(message.getMessage());
		chatForumComment.setComment_by("Pranil");
		chatForumComment.setChat_forum_id("1");
		if(chatForumDAO.addChatForumComment(chatForumComment))
			return new OutputMessage(message, new Date());
		else
			return new OutputMessage(message, new Date());	
	}
}
