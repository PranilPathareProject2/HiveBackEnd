package com.niit.hive.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
			event.setEvent_id(this.nextEventID());
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
	
	@Override
	@Transactional
	public List listActiveEvents() {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Event.class);
		criteria.add(Restrictions.eq("status", "Activated"));
		
		List liab = criteria.list();
		
		return liab;
	}
	
	@Override
	@Transactional
	public boolean updateEventStatus(String event_id, String status) {
		
		try {
			Event event = this.getEvent(event_id);
			event.setStatus(status);
			if(this.updateEvent(event))
			{	
				return true;
			}	
			else
			{	
				return false;
			}	
		} catch (Exception e) {
			return false;
		}		
	}
	
	@Override
	@Transactional
	public String nextEventID() {
		String newID;
		List templist = sessionFactory.getCurrentSession().createQuery("from Event order by event_id desc").list();
		if(templist.size()==0)
		{
			newID="E-001";	
		}
		else
		{
			Event Obj = (Event) templist.get(0);
			String id = Obj.getEvent_id();
			String temp = id.substring(0, 2);
			int tempID = Integer.parseInt(id.substring(2, 5));
			tempID++;
			newID = temp + String.format("%03d", tempID);
		}
		return newID;
	}
}
