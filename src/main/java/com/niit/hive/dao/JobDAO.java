package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.Job;

public interface JobDAO {

		public boolean addJob(Job job);
		public boolean updateJob(Job job);
		public Job getJob(String job_id);
		public List allJobs();
		public String nextJobID();
}
