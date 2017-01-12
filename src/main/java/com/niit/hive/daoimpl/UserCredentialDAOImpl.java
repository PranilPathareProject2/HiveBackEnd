package com.niit.hive.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
			usercred.setReason("");
			sessionFactory.getCurrentSession().update(usercred);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean rejectUser(String username, String reason) {
		try {
			UserCredential usercred = this.getUserCredential(username);
			usercred.setStatus("Rejected");
			usercred.setReason(reason);
			sessionFactory.getCurrentSession().update(usercred);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public UserCredential authenticateUser(String username, String password) {
		@SuppressWarnings("deprecation")
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserCredential.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("password", password));
		criteria.add(Restrictions.eq("status", "Accepted"));
		
		UserCredential usercred = (UserCredential) criteria.uniqueResult();
		return usercred;
	}
	
	@Override
	@Transactional
	public boolean makeAdmin(String username) {
		try {
			UserCredential usercred = this.getUserCredential(username);
			usercred.setRole("ROLE_ADMIN");
			sessionFactory.getCurrentSession().update(usercred);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
