package com.niit.hive.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.hive.dao.UserCredentialDAO;
import com.niit.hive.model.User;
import com.niit.hive.model.UserCredential;

@Repository("userCredentialDAO")
public class UserCredentialDAOImpl implements UserCredentialDAO {

	@Autowired
	public SessionFactory sessionFactory;
	
	public UserCredentialDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean addUserCredential(UserCredential usercred) {
		try {
			sessionFactory.getCurrentSession().save(usercred);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean updateUserCredential(UserCredential usercred) {
		try {
			sessionFactory.getCurrentSession().update(usercred);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public UserCredential getUserCredential(String username) {
		UserCredential usercred = sessionFactory.getCurrentSession().get(UserCredential.class, username);
		return usercred;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public List listUserCredentials() {
		List lius = sessionFactory.getCurrentSession().createQuery("from UserCredential").list();
		return lius;
	}

	@Override
	@Transactional
	public boolean acceptUser(String username) {
		try {
			UserCredential usercred = this.getUserCredential(username);
			usercred.setStatus("Accepted");
			sessionFactory.getCurrentSession().update(usercred);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
