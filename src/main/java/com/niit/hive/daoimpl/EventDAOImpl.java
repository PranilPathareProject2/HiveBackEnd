package com.niit.hive.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.hive.dao.EventDAO;
import com.niit.hive.model.Event;

@Repository("eventDAO")
public class EventDAOImpl implements EventDAO {

	@Autowired
	public SessionFactory sessionFactory;
	
	public EventDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean addEvent(Event event) {
		try {
			sessionFactory.getCurrentSession().save(event);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateEvent(Event event) {
		try {
			sessionFactory.getCurrentSession().update(event);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public Event getEvent(String username) {
		Event event = sessionFactory.getCurrentSession().get(Event.class, username);
		return event;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public List listEvents() {
		List lius = sessionFactory.getCurrentSession().createQuery("from Event").list();
		return lius;
	}
}
