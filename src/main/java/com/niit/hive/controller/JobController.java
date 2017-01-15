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

import com.niit.hive.dao.JobDAO;
import com.niit.hive.model.Job;
import com.niit.hive.model.JobApplied;

@RestController
public class JobController {

	@Autowired
	JobDAO jobDAO;
	
	@Autowired
	Job job;
	
	@Autowired
	JobApplied jobApplied;
	
	@Autowired
	HttpSession session;
	
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
	
	@RequestMapping(value="/getmyappliedjobs", method=RequestMethod.GET)
	public ResponseEntity<List<JobApplied>> getMyAppliedJobs()
	{
		String loggedInUser = (String) session.getAttribute("loggedInUser");
		
		List<JobApplied> jobs = new ArrayList<JobApplied>();
		
		if(loggedInUser.isEmpty() || loggedInUser == null)
		{
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("You have to login to see your applied jobs");
			jobs.add(jobApplied);
		}
		else
		{
			jobs = jobDAO.getMyAppliedJobs(loggedInUser);
		}
		return new ResponseEntity<List<JobApplied>>(jobs, HttpStatus.OK);
	}
	
	@RequestMapping(value="/applyjob/{job_id}", method=RequestMethod.PUT)
	public ResponseEntity<JobApplied> acceptUsers(@PathVariable("job_id") String job_id)
	{
		String loggedInUser = (String) session.getAttribute("loggedInUser");
		
		if(loggedInUser.isEmpty() || loggedInUser == null)
		{
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("You have to login to apply for jobs");
		}
		else
		{
			if(jobDAO.hasUserAppliedForTheJob(loggedInUser, job_id))
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
		String loggedInUser = (String) session.getAttribute("loggedInUser");
		//String loggedInUserRole = (String) session.getAttribute("loggedInUserRole"); check this
		
		if(loggedInUser.isEmpty() || loggedInUser == null)
		{
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("You have to login to do this operation");
			return jobApplied;
		}
		else
		{
			
		}
		return jobApplied;
	}
}
