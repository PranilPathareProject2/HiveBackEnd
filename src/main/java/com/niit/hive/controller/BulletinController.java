package com.niit.hive.controller;

import java.sql.Date;
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

import com.niit.hive.dao.BulletinDAO;
import com.niit.hive.model.Bulletin;

@RestController
public class BulletinController {

	@Autowired
	BulletinDAO bulletinDAO;
	
	@Autowired
	Bulletin bulletin;
	
	@Autowired
	HttpSession httpSession;
	
	@RequestMapping(value="/listbulletins", method=RequestMethod.GET)
	public ResponseEntity<List<Bulletin>> listBulletins()
	{
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");

		List<Bulletin> bulletins = new ArrayList<Bulletin>();
		if(loggedInUserRole == null)
		{
			bulletin.setErrorCode("404");
			bulletin.setErrorMessage("You have to login to manage bulletins");
			bulletins.add(bulletin);
			return new ResponseEntity<List<Bulletin>>(bulletins, HttpStatus.OK);
		}
		
		/*System.out.println("loggedInUserRole = "+loggedInUserRole);
		System.out.println("True or False = "+loggedInUserRole.equals("ROLE_ADMIN"));*/
		if(!loggedInUserRole.equals("ROLE_ADMIN"))
		{	
			bulletin.setErrorCode("404");
			bulletin.setErrorMessage("You are not authorized to manage bulletins");
			bulletins.add(bulletin);
			return new ResponseEntity<List<Bulletin>>(bulletins, HttpStatus.OK);
		}
		else
		{	
			bulletins = bulletinDAO.listBulletins();
			
			if(bulletins.isEmpty())
			{
				bulletin.setErrorCode("404");
				bulletin.setErrorMessage("You have no bulletins to manage");
				bulletins.add(bulletin);
			}
		}
		
		return new ResponseEntity<List<Bulletin>>(bulletins, HttpStatus.OK);
	}
	
	@RequestMapping(value="/activebulletins", method=RequestMethod.GET)
	public ResponseEntity<List<Bulletin>> activeBulletins()
	{
		List<Bulletin> bulletins = bulletinDAO.listActiveBulletins();
		return new ResponseEntity<List<Bulletin>>(bulletins, HttpStatus.OK);
	}
	
	@RequestMapping(value="/activatebulletin/{bulletin_id}", method=RequestMethod.PUT)
	public ResponseEntity<Bulletin> activateBulletin(@PathVariable("bulletin_id") String bulletin_id)
	{
		bulletin = updateStatus(bulletin_id, "Activated");
		return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
	}

	@RequestMapping(value="/deactivatebulletin/{bulletin_id}", method=RequestMethod.PUT)
	public ResponseEntity<Bulletin> deactivateBulletin(@PathVariable("bulletin_id") String bulletin_id)
	{
		bulletin = updateStatus(bulletin_id, "Deactivated");
		return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
	}
	
	private Bulletin updateStatus(String bulletin_id, String status)
	{
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");
		
		if(loggedInUserRole == null)
		{
			bulletin.setErrorCode("404");
			bulletin.setErrorMessage("You have to login to update bulletin status");
			//return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		else if(!loggedInUserRole.equals("ROLE_ADMIN"))
		{
			bulletin.setErrorCode("404");
			bulletin.setErrorMessage("You are not authorized to update bulletin status");
		}
		else
		{
			if(bulletinDAO.updateBulletinStatus(bulletin_id, status))
			{	
				bulletin.setErrorCode("200");
				bulletin.setErrorMessage("Bulletin status updated successfully");
			}
			else
			{
				bulletin.setErrorCode("404");
				bulletin.setErrorMessage("Bulletin status was not updated");
			}	
		}
		return bulletin;
	}
	
	@RequestMapping(value="/addbulletin", method=RequestMethod.POST)
	public ResponseEntity<Bulletin> addBulletin(@RequestBody Bulletin bulletin)
	{
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");

		if(loggedInUserRole == null)
		{
			bulletin.setErrorCode("404");
			bulletin.setErrorMessage("You have to login to add a bulletin");
			return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
		}
		
		if(!loggedInUserRole.equals("ROLE_ADMIN"))
		{
			bulletin.setErrorCode("404");
			bulletin.setErrorMessage("You are not authorized to add a bulletin");			
		}
		else
		{
			if(bulletinDAO.addBulletin(bulletin))
			{
				bulletin.setErrorCode("200");
				bulletin.setErrorMessage("Bulletin added successfully");
			}
			else
			{
				bulletin.setErrorCode("404");
				bulletin.setErrorMessage("Bulletin not added");
			}
		}
		return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatebulletin", method=RequestMethod.PUT)
	public ResponseEntity<Bulletin> updateBulletin(@RequestBody Bulletin bulletin)
	{
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");

		if(loggedInUserRole == null)
		{
			bulletin.setErrorCode("404");
			bulletin.setErrorMessage("You have to login to update a bulletin");
			return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
		}
		
		if(!loggedInUserRole.equals("ROLE_ADMIN"))
		{
			bulletin.setErrorCode("404");
			bulletin.setErrorMessage("You are not authorized to update a bulletin");			
		}
		else
		{
			if(bulletinDAO.updateBulletin(bulletin))
			{
				bulletin.setErrorCode("200");
				bulletin.setErrorMessage("Bulletin updated successfully");
			}
			else
			{
				bulletin.setErrorCode("404");
				bulletin.setErrorMessage("Bulletin not updated");
			}
		}
		return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getbulletin/{bulletin_id}", method=RequestMethod.GET)
	public ResponseEntity<Bulletin> getBulletinToUpdate(@PathVariable("bulletin_id") String bulletin_id)
	{
		bulletin = bulletinDAO.getBulletin(bulletin_id);
		if(bulletin == null)
		{
			bulletin = new Bulletin();
			bulletin.setErrorCode("404");
			bulletin.setErrorMessage("The requested bulletin is not available");			
		}
		
		return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
	}
}
