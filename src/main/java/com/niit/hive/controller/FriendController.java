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

import com.niit.hive.dao.FriendDAO;
import com.niit.hive.dao.UserDAO;
import com.niit.hive.model.Friend;


@RestController
public class FriendController {

	@Autowired
	FriendDAO friendDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	Friend friend;
	
	@Autowired
	HttpSession httpSession;
	
	@RequestMapping(value="/myfriends", method=RequestMethod.GET)
	public ResponseEntity<List<Friend>> listFriends()
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");

		List<Friend> friends = new ArrayList<Friend>();
		if(loggedInUser == null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have to login to see your friends");
			friends.add(friend);
			return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
		}
		
		friends = friendDAO.listFriends(loggedInUser);
		
		if(friends.isEmpty())
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have no friends");
			friends.add(friend);
		}
		
		return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addfriendrequest/{friend_username}", method=RequestMethod.POST)
	public ResponseEntity<Friend> addFriendRequest(@PathVariable("friend_username") String friend_username)
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");

		System.out.println("loggedInUser = "+loggedInUser);
		if(loggedInUser == null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have to login to send friend request");
			//return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		else if(isUserExists(friend_username))
		{
			System.out.println("isUserExists(friend_username) = "+isUserExists(friend_username));
			friend.setErrorCode("404");
			friend.setErrorMessage("Friend with username: "+friend_username+" does not exists");
		}
		else if(friendDAO.getFriendship(loggedInUser, friend_username)!=null || friendDAO.getFriendship(friend_username, loggedInUser)!=null)
		{
			System.out.println("friendDAO.getFriendship(loggedInUser, friend_username) ="+friendDAO.getFriendship(loggedInUser, friend_username));
			friend.setErrorCode("404");
			friend.setErrorMessage("You have already send this friend request");
		}
		else if(loggedInUser.equals(friend_username))
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You cannot send friend request to yourself");
		}
		else
		{
			friend.setUser_username(loggedInUser);
			friend.setFriend_username(friend_username);
			friendDAO.addFriendRequest(friend);
			friend.setErrorCode("200");
			friend.setErrorMessage("Successfully sent the friend request");
		}
		
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	private boolean isUserExists(String friend_username) {
		if(userDAO.getUser(friend_username)!=null)
			return false;
		else
			return true;
	}
	
	@RequestMapping(value="/acceptfriendrequest/{friend_username}", method=RequestMethod.PUT)
	public ResponseEntity<Friend> acceptFriendRequest(@PathVariable("friend_username") String friend_username)
	{
		friend = updateStatus(friend_username, "Accepted");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value="/unfriend/{friend_username}", method=RequestMethod.PUT)
	public ResponseEntity<Friend> unfriend(@PathVariable("friend_username") String friend_username)
	{
		friend = updateStatus(friend_username, "Unfriended");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectfriendrequest/{friend_username}", method=RequestMethod.PUT)
	public ResponseEntity<Friend> rejectFriendRequest(@PathVariable("friend_username") String friend_username)
	{
		friend = updateStatus(friend_username, "Rejected");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}
	
	private Friend updateStatus(String friend_username, String status)
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");
		//String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");
		
		System.out.println("loggedInUser = "+loggedInUser);
		if(loggedInUser == null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have to login to update friend request");
			//return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		else if(status.equals("Accepted") || status.equals("Rejected"))
		{
			if(isFriendRequestExists(friend_username, loggedInUser))
			{
				friend.setErrorCode("404");
				friend.setErrorMessage("The Friend request does not exists");
				//return friend;
			}
			else
			{	
			friend = friendDAO.getFriendship(friend_username, loggedInUser);
			friend.setStatus(status);
			friendDAO.updateFriendship(friend);
			
			friend.setErrorCode("200");
			friend.setErrorMessage("Friend request updated successfully");
			}
		}
		else
		{
			if(isFriendRequestExists(loggedInUser, friend_username))
			{
				friend.setErrorCode("404");
				friend.setErrorMessage("The Friend request does not exists");
				//return friend;
			}
			else
			{
			friend = friendDAO.getFriendship(loggedInUser, friend_username);
			friend.setStatus(status);
			friendDAO.updateFriendship(friend);
			
			friend.setErrorCode("200");
			friend.setErrorMessage("Friend request updated successfully");
			}
		}
	
		return friend;
	}
	
	private boolean isFriendRequestExists(String friend_username, String loggedInUser) {
		if(friendDAO.getFriendship(friend_username, loggedInUser)!=null)
			return false;
		else	
			return true;
	}

	@RequestMapping(value="/getnewfriendrequests", method=RequestMethod.GET)
	public ResponseEntity<List<Friend>> listFriendRequests()
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");

		List<Friend> friendrequests = new ArrayList<Friend>();
		if(loggedInUser == null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have to login to see your new friend requests");
			friendrequests.add(friend);
			return new ResponseEntity<List<Friend>>(friendrequests, HttpStatus.OK);
		}
		
		friendrequests = friendDAO.getNewFriendRequests(loggedInUser);
		
		if(friendrequests.isEmpty())
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have no new friend requests");
			friendrequests.add(friend);
		}
		
		return new ResponseEntity<List<Friend>>(friendrequests, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getsentfriendrequests", method=RequestMethod.GET)
	public ResponseEntity<List<Friend>> listSentFriendRequests()
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");

		List<Friend> sentfriendrequests = new ArrayList<Friend>();
		if(loggedInUser == null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have to login to see your sent friend requests");
			sentfriendrequests.add(friend);
			return new ResponseEntity<List<Friend>>(sentfriendrequests, HttpStatus.OK);
		}
		
		sentfriendrequests = friendDAO.getSentFriendRequests(loggedInUser);
		
		if(sentfriendrequests.isEmpty())
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have not sent any friend requests");
			sentfriendrequests.add(friend);
		}
		
		return new ResponseEntity<List<Friend>>(sentfriendrequests, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getonlinefriends", method=RequestMethod.GET)
	public ResponseEntity<List<Friend>> listOnlineFriends()
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");

		List<Friend> onlinefriends = new ArrayList<Friend>();
		if(loggedInUser == null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have to login to see your online friends");
			onlinefriends.add(friend);
			return new ResponseEntity<List<Friend>>(onlinefriends, HttpStatus.OK);
		}
		
		onlinefriends = friendDAO.getOnlineFriends(loggedInUser);
		
		if(onlinefriends.isEmpty())
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have no new friend requests");
			onlinefriends.add(friend);
		}
		
		return new ResponseEntity<List<Friend>>(onlinefriends, HttpStatus.OK);
	}
}
