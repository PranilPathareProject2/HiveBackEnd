package com.niit.daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.dao.UserDAO;
import com.niit.model.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void addUser(User user) {
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		session.save(user);
		tran.commit();
		session.close();
	}

	@Override
	public void deleteUser(String username) {
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		User user = session.get(User.class, username);
		session.delete(user);
		tran.commit();
		session.close();
	}

	@Override
	public void updateUser(String username) {
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		User user = session.get(User.class, username);
		user.setPassword("pranil*20");
		session.update(user);
		tran.commit();
		session.close();
	}
}
