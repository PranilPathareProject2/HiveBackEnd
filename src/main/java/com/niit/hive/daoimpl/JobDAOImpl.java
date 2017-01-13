package com.niit.hive.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.hive.dao.JobDAO;
import com.niit.hive.model.Job;

@Repository("jobDAO")
public class JobDAOImpl implements JobDAO {

	@Autowired
	public SessionFactory sessionFactory;
	
	public JobDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean addJob(Job job) {
		try {
			job.setJob_id(this.nextJobID());
			sessionFactory.getCurrentSession().save(job);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateJob(Job job) {
		try {
			sessionFactory.getCurrentSession().update(job);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public Job getJob(String job_id) {
		Job job = sessionFactory.getCurrentSession().get(Job.class, job_id);
		return job;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public List allJobs() {
		List lijs = sessionFactory.getCurrentSession().createQuery("from Job").list();
		return lijs;
	}

	@Override
	@Transactional
	public String nextJobID() {
		String newID;
		List templist = sessionFactory.getCurrentSession().createQuery("from Job order by job_id desc").list();
		if(templist.size()==0)
		{
			newID="J-001";	
		}
		else
		{
		Job Obj = (Job) templist.get(0);
		String id = Obj.getJob_id();
		String temp = id.substring(0, 2);
		int tempID = Integer.parseInt(id.substring(2, 5));
		tempID++;
		newID = temp + String.format("%03d", tempID);
		}
		return newID;
	}
}
