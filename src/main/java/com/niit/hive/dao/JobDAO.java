package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.Job;
import com.niit.hive.model.JobApplied;

public interface JobDAO {

		public boolean addJob(Job job);
		public boolean updateJob(Job job);
		public Job getJob(String job_id);
		public List allJobs();
		public String nextJobID();
		public List getMyAppliedJobs(String username);
		public boolean applyJob(JobApplied job);
		public String nextJobAppliedID();
		public boolean hasUserAppliedForTheJob(String username, String job_id);
}
