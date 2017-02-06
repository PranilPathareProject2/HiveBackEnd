package com.niit.hive.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.hive.dao.ChatForumDAO;
import com.niit.hive.model.ChatForum;
import com.niit.hive.model.ChatForumComment;
import com.niit.hive.model.Job;
import com.niit.hive.model.Message;
import com.niit.hive.model.OutputMessage;

@Controller
@RestController
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
		/*chatForumComment.setChat_forum_comment(message.getMessage());
		chatForumComment.setComment_by("Pranil");
		chatForumComment.setChat_forum_id("1");
		if(chatForumDAO.addChatForumComment(chatForumComment))
			return new OutputMessage(message, new Date());
		else*/
			return new OutputMessage(message, new Date());	
	}
	
	@RequestMapping(value="/addforum", method=RequestMethod.POST)
	public ResponseEntity<ChatForum> register(@RequestBody ChatForum chatforum)
	{
		if(!chatForumDAO.addForum(chatforum))
		{
			chatforum = new ChatForum();
			chatforum.setErrorCode("404");
			chatforum.setErrorMessage("Adding the Forum was not a success");
		}
		else
		{
			chatforum.setErrorCode("200");
			chatforum.setErrorMessage("Successfully added the forum");
		}
		return new ResponseEntity<ChatForum>(chatforum, HttpStatus.OK);
	}
}
