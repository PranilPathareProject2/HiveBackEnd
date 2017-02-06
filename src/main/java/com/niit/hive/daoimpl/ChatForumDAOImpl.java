package com.niit.hive.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.hive.dao.ChatForumDAO;
import com.niit.hive.model.ChatForum;
import com.niit.hive.model.ChatForumComment;

@Repository("chatForumDAO")
public class ChatForumDAOImpl implements ChatForumDAO {

	@Autowired
	public SessionFactory sessionFactory;
	
	public ChatForumDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean addForum(ChatForum chatforum) {
		try {
			chatforum.setChat_forum_id(nextChatForumID());
			sessionFactory.getCurrentSession().save(chatforum);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean addChatForumComment(ChatForumComment chatforumcomment) {
		try {
			chatforumcomment.setChat_forum_comment_id(nextChatForumCommentID());
			sessionFactory.getCurrentSession().save(chatforumcomment);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*@Override
	@Transactional
	public boolean updateChatForumComment(ChatForumComment chatforumcomment) {
		try {
			sessionFactory.getCurrentSession().update(chatforumcomment);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public ChatForumComment getChatForumComment(String chatforumcomment_id) {
		ChatForumComment chatforumcomment = sessionFactory.getCurrentSession().get(ChatForumComment.class, chatforumcomment_id);
		return chatforumcomment;
	}*/
	
	@Override
	@Transactional
	public String nextChatForumCommentID() {
		String newID;
		List templist = sessionFactory.getCurrentSession().createQuery("from ChatForumComment order by chat_forum_comment_id desc").list();
		if(templist.size()==0)
		{
			newID="CFC-001";	
		}
		else
		{
			ChatForumComment Obj = (ChatForumComment) templist.get(0);
			String id = Obj.getChat_forum_comment_id();
			String temp = id.substring(0, 4);
			int tempID = Integer.parseInt(id.substring(4, 7));
			tempID++;
			newID = temp + String.format("%03d", tempID);
		}
		return newID;
	}

	@Override
	@Transactional
	public String nextChatForumID() {
		String newID;
		List templist = sessionFactory.getCurrentSession().createQuery("from ChatForum order by chat_forum_id desc").list();
		if(templist.size()==0)
		{
			newID="CF-001";	
		}
		else
		{
			ChatForum Obj = (ChatForum) templist.get(0);
			String id = Obj.getChat_forum_id();
			String temp = id.substring(0, 3);
			int tempID = Integer.parseInt(id.substring(3, 6));
			tempID++;
			newID = temp + String.format("%03d", tempID);
		}
		return newID;
	}

	@Override
	@Transactional
	public List listChatForumCommentsByChatForum(String chatforum_id) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ChatForumComment.class);
		criteria.add(Restrictions.eq("chat_forum_id", chatforum_id));
		
		List licfcbcf = criteria.list();
		
		return licfcbcf;
	}
}
