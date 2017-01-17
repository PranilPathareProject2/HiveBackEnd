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
	public ResponseEntity<Friend> register(@PathVariable("friend_username") String friend_username)
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");

		if(loggedInUser == null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have to login to send friend request");
		}
		
		if(isUserExists(friend_username))
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("Friend with username: "+friend_username+" does not exists");
		}
		
		if(friendDAO.getFriendship(loggedInUser, friend_username)!=null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You have already send this friend request");
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
		friend = updateStatus(friend_username, "Unfriend");
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
		
		if(isFriendRequestExists(loggedInUser, friend_username))
		{
			
		}
		
		if(status.equals("Accepted") || status.equals("Rejected"))
		{
			friend = friendDAO.getFriendship(friend_username, loggedInUser);
		}
		else
		{
			friend = friendDAO.getFriendship(loggedInUser, friend_username);
		}
		
		friend.setStatus(status);
		friendDAO.updateFriendship(friend);
		
		friend.setErrorCode("200");
		friend.setErrorMessage("Friend request updated successfully");
		return friend;
	}
	
	private boolean isFriendRequestExists(String loggedInUser, String friend_username) {
		if(friendDAO.getFriendship(loggedInUser, friend_username)!=null)
			return false;
		else	
			return true;
	}

	/*@RequestMapping(value="/updatejob", method=RequestMethod.PUT)
	public ResponseEntity<Job> updateUserInDB(@RequestBody Job job)
	{
		if(jobDAO.getJob(job.getJob_id())==null)
		{
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("Updating Job was not a success");
		}
		else
		{
			jobDAO.updateJob(job);
			job.setErrorCode("200");
			job.setErrorMessage("Updating Job was successful");
		}
		
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}*/
	
	/*@RequestMapping(value="/alljobs", method=RequestMethod.GET)
	public ResponseEntity<List<Job>> listUsers()
	{
		List<Job> jobs = jobDAO.allJobs();
		
		if(jobs.isEmpty())
		{
			job.setErrorCode("404");
			job.setErrorMessage("No jobs are available");
			jobs.add(job);
		}
		
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getalljobapplications", method=RequestMethod.GET)
	public ResponseEntity<List<JobApplied>> listjobapplications()
	{
		List<JobApplied> jobapplications = jobDAO.allJobApplications();
		
		if(jobapplications.isEmpty())
		{
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("No jobs are available");
			jobapplications.add(jobApplied);
		}
		
		return new ResponseEntity<List<JobApplied>>(jobapplications, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getjobdetails/{job_id}", method=RequestMethod.GET)
	public ResponseEntity<Job> getJobById(@PathVariable("job_id") String job_id)
	{
		job = jobDAO.getJob(job_id);
		
		if(job == null)
		{
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("The requested job is not found");
		}
		
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getmyappliedjobs", method=RequestMethod.GET)
	public ResponseEntity<List<JobApplied>> getMyAppliedJobs()
	{
		String username = (String) httpSession.getAttribute("loggedInUser");
		System.out.println("********username**********="+username);
		List<JobApplied> jobs = new ArrayList<JobApplied>();
		
		if(username == null)
		{
			//jobApplied = new JobApplied();
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("You have to login to see your applied jobs");
			jobs.add(jobApplied);
		}
		else
		{
			jobs = jobDAO.getMyAppliedJobs(username);
			if(jobs.isEmpty())
			{
				jobApplied.setErrorCode("404");
				jobApplied.setErrorMessage("No applied jobs for this user");
				jobs.add(jobApplied);
			}	
		}
		return new ResponseEntity<List<JobApplied>>(jobs, HttpStatus.OK);
	}
	
	@RequestMapping(value="/applyjob/{job_id}", method=RequestMethod.POST)
	public ResponseEntity<JobApplied> applyJob(@PathVariable("job_id") String job_id)
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");
		//System.out.println("job id ="+job_id);
		if(loggedInUser == null)
		{
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("You have to login to apply for jobs");
		}
		else
		{
			if(jobDAO.getJobApplication(loggedInUser, job_id)==null)
			{
				jobApplied.setJob_id(job_id);
				jobApplied.setUsername(loggedInUser);
				//jobApplied.setApplied_date(new Date(System.currentTimeMillis()));
				
				if(jobDAO.applyJob(jobApplied))
				{
					jobApplied.setErrorCode("200");
					jobApplied.setErrorMessage("You have successfully applied for this job");
				}
			}
			else
			{
				jobApplied.setErrorCode("404");
				jobApplied.setErrorMessage("You have already applied for this job");
			}
		}
		return new ResponseEntity<JobApplied>(jobApplied, HttpStatus.OK);
	}
	
	@RequestMapping(value="/selectuser/{job_id}/{username}/{reason}", method=RequestMethod.PUT)
	public ResponseEntity<JobApplied> selectUser(@PathVariable("username") String username, @PathVariable("reason") String reason, @PathVariable("job_id") String job_id)
	{
		jobApplied = updateStatus(username, job_id, reason, "Selected");
		return new ResponseEntity<JobApplied>(jobApplied, HttpStatus.OK);
	}

	@RequestMapping(value="/callforinterview/{job_id}/{username}/{reason}", method=RequestMethod.PUT)
	public ResponseEntity<JobApplied> callForInterview(@PathVariable("username") String username, @PathVariable("reason") String reason, @PathVariable("job_id") String job_id)
	{
		jobApplied = updateStatus(username, job_id, reason, "Called For Interview");
		return new ResponseEntity<JobApplied>(jobApplied, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectjobapplication/{job_id}/{username}/{reason}", method=RequestMethod.PUT)
	public ResponseEntity<JobApplied> rejectJobApplication(@PathVariable("username") String username, @PathVariable("reason") String reason, @PathVariable("job_id") String job_id)
	{
		jobApplied = updateStatus(username, job_id, reason, "Rejected");
		return new ResponseEntity<JobApplied>(jobApplied, HttpStatus.OK);
	}
	
	private JobApplied updateStatus(String username, String job_id, String reason, String status)
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");
		
		if(loggedInUserRole == null)
		{
			//jobApplied = new JobApplied();
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("You have to login to do this operation");
			return jobApplied;
		}
		
		String checkrole = "ROLE_ADMIN";
		boolean check = loggedInUserRole.equalsIgnoreCase(checkrole);
		
		if(!check)
		{
			//jobApplied = new JobApplied();
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("You are not authorized to do this operation");
			return jobApplied;
		}
		else
		{
			jobApplied = jobDAO.getJobApplication(username, job_id);
		
			jobApplied.setStatus(status);
			jobApplied.setReason(reason);
		
			if(jobDAO.updateJobApplication(jobApplied))
			{
				jobApplied.setErrorCode("200");
				jobApplied.setErrorMessage("Successfully updated the status");
			}
			else
			{
				jobApplied.setErrorCode("404");
				jobApplied.setErrorMessage("Updating the status was not successful");
			}
			return jobApplied;
		}
	}*/
}
