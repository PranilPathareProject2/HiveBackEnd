package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.Event;

public interface EventDAO {

		public boolean addEvent(Event event);
		public boolean updateEvent(Event event_id);
		public Event getEvent(String event_id);
		public List listEvents();
		public List listActiveEvents();
		public boolean updateEventStatus(String event_id, String status);
		public String nextEventID();
}
