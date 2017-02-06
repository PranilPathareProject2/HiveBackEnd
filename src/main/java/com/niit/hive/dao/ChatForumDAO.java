package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.ChatForum;
import com.niit.hive.model.ChatForumComment;

public interface ChatForumDAO {

	public boolean addChatForumComment(ChatForumComment chatforumcomment);

	//public boolean updateChatForumComment(ChatForumComment chatforumcomment);

	//public ChatForumComment getChatForumComment(String chatforumcomment_id);

	public String nextChatForumCommentID();

	public String nextChatForumID();

	public List listChatForumCommentsByChatForum(String chatforum_id);

	public boolean addForum(ChatForum chatforum);

}
