package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.Event;

public interface EventDAO {

		public boolean addEvent(Event event);
		public boolean updateEvent(String username);
		public Event getEvent(String event_id);
		public List listEvents();
}
