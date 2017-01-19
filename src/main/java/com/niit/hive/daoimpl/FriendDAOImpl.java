package com.niit.hive.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.hive.dao.FriendDAO;
import com.niit.hive.model.Friend;
import com.niit.hive.model.Job;

@Repository("friendDAO")
public class FriendDAOImpl implements FriendDAO {

	@Autowired
	public SessionFactory sessionFactory;
	
	public FriendDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean addFriendRequest(Friend friend) {
		try {
			friend.setFriend_id(this.nextFriendID());
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateFriendship(Friend friend) {
		try {
			sessionFactory.getCurrentSession().update(friend);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public Friend getFriendship(String user_username, String friend_username) {
				
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Friend.class);
		criteria.add(Restrictions.eq("user_username", user_username));
		criteria.add(Restrictions.eq("friend_username", friend_username));
		
		Friend friend = (Friend) criteria.uniqueResult();
		
		return friend;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public List listFriends(String user_username) {
		
		String hql1 = "select friend_username from Friend where user_username='"+user_username+"' and status='Accepted'";
		String hql2 = "select user_username from Friend where friend_username='"+user_username+"' and status='Accepted'";
		
		Query q1 = sessionFactory.getCurrentSession().createQuery(hql1);
		Query q2 = sessionFactory.getCurrentSession().createQuery(hql2);
		
		List lif1 = q1.list();
		List lif2 = q2.list();
		
		lif1.addAll(lif2);
		
		return lif1;
	}
	
	@Transactional
	public String nextFriendID() {
		String newID;
		List templist = sessionFactory.getCurrentSession().createQuery("from Friend order by friend_id desc").list();
		if(templist.size()==0)
		{
			newID="F-001";	
		}
		else
		{
		Friend Obj = (Friend) templist.get(0);
		String id = Obj.getFriend_id();
		String temp = id.substring(0, 2);
		int tempID = Integer.parseInt(id.substring(2, 5));
		tempID++;
		newID = temp + String.format("%03d", tempID);
		}
		return newID;
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	@Transactional
	public void setOnline(String user_username) {
		
		String hql1 = "update Friend set is_online='Yes' where user_username='"+user_username+"'";
		String hql2 = "update Friend set is_online='Yes' where friend_username='"+user_username+"'";
		
		Query q1 = sessionFactory.getCurrentSession().createQuery(hql1);
		Query q2 = sessionFactory.getCurrentSession().createQuery(hql2);
		
		q1.executeUpdate();
		q2.executeUpdate();
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	@Transactional
	public void setOffline(String user_username) {
		
		String hql1 = "update Friend set is_online='No' where user_username='"+user_username+"'";
		String hql2 = "update Friend set is_online='No' where friend_username='"+user_username+"'";
		
		Query q1 = sessionFactory.getCurrentSession().createQuery(hql1);
		Query q2 = sessionFactory.getCurrentSession().createQuery(hql2);
		
		q1.executeUpdate();
		q2.executeUpdate();
	}

	@Override
	@Transactional
	public List getNewFriendRequests(String user_username) {
		
		@SuppressWarnings("deprecation")
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Friend.class);
		criteria.add(Restrictions.eq("friend_username", user_username));
		criteria.add(Restrictions.eq("status", "Pending"));
		
		List linfr = criteria.list();
		
		return linfr;
	}
	
	@Override
	@Transactional
	public List getSentFriendRequests(String user_username) {
		
		@SuppressWarnings("deprecation")
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Friend.class);
		criteria.add(Restrictions.eq("user_username", user_username));
		criteria.add(Restrictions.eq("status", "Pending"));
		
		List lisfr = criteria.list();
		
		return lisfr;
	}
	
	@Override
	@Transactional
	public List getOnlineFriends(String user_username) {
		
		String hql1 = "select friend_username from Friend where user_username='"+user_username+"' and status='Accepted' and is_online='Yes'";
		String hql2 = "select user_username from Friend where friend_username='"+user_username+"' and status='Accepted' and is_online='Yes'";
		
		Query q1 = sessionFactory.getCurrentSession().createQuery(hql1);
		Query q2 = sessionFactory.getCurrentSession().createQuery(hql2);
		
		List lifo1 = q1.list();
		List lifo2 = q2.list();
		
		lifo1.addAll(lifo2);
		
		return lifo1;
	}
}
