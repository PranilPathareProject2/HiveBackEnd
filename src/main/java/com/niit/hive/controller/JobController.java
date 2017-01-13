package com.niit.hive.controller;

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

import com.niit.hive.dao.JobDAO;
import com.niit.hive.model.Job;

@RestController
public class JobController {

	@Autowired
	JobDAO jobDAO;
	
	@Autowired
	Job job;
	
	@RequestMapping(value="/postjob", method=RequestMethod.POST)
	public ResponseEntity<Job> register(@RequestBody Job job)
	{
		if(!jobDAO.addJob(job))
		{
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("Posting the Job was not a success");
		}
		else
		{
			job.setErrorCode("200");
			job.setErrorMessage("Successfully posted the Job");
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatejob", method=RequestMethod.PUT)
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
	}
	
	@RequestMapping(value="/alljobs", method=RequestMethod.GET)
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
	
	/*@RequestMapping(value="/manageusers", method=RequestMethod.GET)
	public ResponseEntity<List<UserCredential>> manageUsers()
	{
		List<UserCredential> users = userCredentialDAO.listUserCredentials();
		
		if(users.isEmpty())
		{
			userCredential.setErrorCode("404");
			userCredential.setErrorMessage("No users are available");
			users.add(userCredential);
		}
		
		return new ResponseEntity<List<UserCredential>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value="/acceptuser/{username}", method=RequestMethod.PUT)
	public ResponseEntity<UserCredential> acceptUsers(@PathVariable("username") String username)
	{
		if(!userCredentialDAO.acceptUser(username))
		{
			userCredential = new UserCredential();
			userCredential.setErrorCode("404");
			userCredential.setErrorMessage("Accepting was not a success");
		}
		else
		{
			userCredential.setErrorCode("200");
			userCredential.setErrorMessage("Accepting was successful");
		}
		
		return new ResponseEntity<UserCredential>(userCredential, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectuser/{username}/{reason}", method=RequestMethod.PUT)
	public ResponseEntity<UserCredential> rejectUsers(@PathVariable("username") String username, @PathVariable("reason") String reason)
	{
		if(!userCredentialDAO.rejectUser(username, reason))
		{
			userCredential = new UserCredential();
			userCredential.setErrorCode("404");
			userCredential.setErrorMessage("Rejecting was not a success");
		}
		else
		{
			userCredential.setErrorCode("200");
			userCredential.setErrorMessage("Rejecting was successful");
		}
		
		return new ResponseEntity<UserCredential>(userCredential, HttpStatus.OK);
	}
	
	@RequestMapping(value="/makeadmin/{username}", method=RequestMethod.PUT)
	public ResponseEntity<UserCredential> makeAdmin(@PathVariable("username") String username)
	{
		if(!userCredentialDAO.makeAdmin(username))
		{
			userCredential = new UserCredential();
			userCredential.setErrorCode("404");
			userCredential.setErrorMessage("Making admin was not a success");
		}
		else
		{
			userCredential.setErrorCode("200");
			userCredential.setErrorMessage("Making admin was successful");
		}
		
		return new ResponseEntity<UserCredential>(userCredential, HttpStatus.OK);
	}*/
	
}
