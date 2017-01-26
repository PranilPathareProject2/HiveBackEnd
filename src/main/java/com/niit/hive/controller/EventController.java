package com.niit.hive.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.hive.dao.EventDAO;
import com.niit.hive.model.Event;

@RestController
public class EventController {

	@Autowired
	EventDAO eventDAO;
	
	@Autowired
	Event event;
	
	@Autowired
	HttpSession httpSession;
	
	@RequestMapping(value="/listevents", method=RequestMethod.GET)
	public ResponseEntity<List<Event>> listEvents()
	{
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");

		List<Event> events = new ArrayList<Event>();
		if(loggedInUserRole == null)
		{
			event.setErrorCode("404");
			event.setErrorMessage("You have to login to manage events");
			events.add(event);
			return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
		}
		
		/*System.out.println("loggedInUserRole = "+loggedInUserRole);
		System.out.println("True or False = "+loggedInUserRole.equals("ROLE_ADMIN"));*/
		if(!loggedInUserRole.equals("ROLE_ADMIN"))
		{	
			event.setErrorCode("404");
			event.setErrorMessage("You are not authorized to manage events");
			events.add(event);
			return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
		}
		else
		{	
			events = eventDAO.listEvents();
			
			if(events.isEmpty())
			{
				event.setErrorCode("404");
				event.setErrorMessage("You have no events to manage");
				events.add(event);
			}
		}
		
		return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	}
	
	@RequestMapping(value="/activeevents", method=RequestMethod.GET)
	public ResponseEntity<List<Event>> activeEvents()
	{
		List<Event> events = eventDAO.listActiveEvents();
		return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	}
	
	@RequestMapping(value="/activateevent/{event_id}", method=RequestMethod.PUT)
	public ResponseEntity<Event> activateEvent(@PathVariable("event_id") String event_id)
	{
		event = updateStatus(event_id, "Activated");
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}

	@RequestMapping(value="/deactivateevent/{event_id}", method=RequestMethod.PUT)
	public ResponseEntity<Event> deactivateEvent(@PathVariable("event_id") String event_id)
	{
		event = updateStatus(event_id, "Deactivated");
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}
	
	private Event updateStatus(String event_id, String status)
	{
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");
		
		if(loggedInUserRole == null)
		{
			event.setErrorCode("404");
			event.setErrorMessage("You have to login to update event status");
			//return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		else if(!loggedInUserRole.equals("ROLE_ADMIN"))
		{
			event.setErrorCode("404");
			event.setErrorMessage("You are not authorized to update event status");
		}
		else
		{
			if(eventDAO.updateEventStatus(event_id, status))
			{	
				event.setErrorCode("200");
				event.setErrorMessage("Event status updated successfully");
			}
			else
			{
				event.setErrorCode("404");
				event.setErrorMessage("Event status was not updated");
			}	
		}
		return event;
	}
	
	@RequestMapping(value="/addevent", method=RequestMethod.POST)
	public ResponseEntity<Event> addEvent(@RequestBody Event event)
	{
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");

		if(loggedInUserRole == null)
		{
			event.setErrorCode("404");
			event.setErrorMessage("You have to login to add a event");
			return new ResponseEntity<Event>(event, HttpStatus.OK);
		}
		
		if(!loggedInUserRole.equals("ROLE_ADMIN"))
		{
			event.setErrorCode("404");
			event.setErrorMessage("You are not authorized to add a event");			
		}
		else
		{
			if(eventDAO.addEvent(event))
			{
				event.setErrorCode("200");
				event.setErrorMessage("Event added successfully");
			}
			else
			{
				event.setErrorCode("404");
				event.setErrorMessage("Event not added");
			}
		}
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateevent", method=RequestMethod.PUT)
	public ResponseEntity<Event> updateEvent(@RequestBody Event event)
	{
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");

		if(loggedInUserRole == null)
		{
			event.setErrorCode("404");
			event.setErrorMessage("You have to login to update a event");
			return new ResponseEntity<Event>(event, HttpStatus.OK);
		}
		
		if(!loggedInUserRole.equals("ROLE_ADMIN"))
		{
			event.setErrorCode("404");
			event.setErrorMessage("You are not authorized to update a event");			
		}
		else
		{
			if(eventDAO.updateEvent(event))
			{
				event.setErrorCode("200");
				event.setErrorMessage("Event updated successfully");
			}
			else
			{
				event.setErrorCode("404");
				event.setErrorMessage("Event not updated");
			}
		}
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getevent/{event_id}", method=RequestMethod.GET)
	public ResponseEntity<Event> getEventToUpdate(@PathVariable("event_id") String event_id)
	{
		event = eventDAO.getEvent(event_id);
		if(event == null)
		{
			event = new Event();
			event.setErrorCode("404");
			event.setErrorMessage("The requested event is not available");			
		}
		
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}
}
