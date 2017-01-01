package com.niit.hive.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.hive.dao.UserDAO;
import com.niit.hive.model.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	@Autowired
	public SessionFactory sessionFactory;
	
	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean addUser(User user) {
		try {
			sessionFactory.getCurrentSession().save(user);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean updateUser(User user) {
		try {
			sessionFactory.getCurrentSession().update(user);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public User getUser(int username) {
		User user = sessionFactory.getCurrentSession().get(User.class, username);
		return user;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public List listUsers() {
		List lius = sessionFactory.getCurrentSession().createQuery("from User").list();
		return lius;
	}
}
