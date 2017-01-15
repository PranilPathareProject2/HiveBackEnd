package com.niit.hive.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.hive.dao.JobDAO;
import com.niit.hive.model.Job;
import com.niit.hive.model.JobApplied;

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
			if(job.getJob_salary() == null)
			{
				job.setJob_salary("Not Disclosed");
			}
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
			if(job.getJob_salary() == null)
			{
				job.setJob_salary("Not Disclosed");
			}
			if(job.getStatus() == null)
			{
				job.setStatus("Active");
			}
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

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public List getMyAppliedJobs(String username) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JobApplied.class);
		criteria.add(Restrictions.eq("username", username));
		
		List limajs = criteria.list();
		return limajs;
	}
	
	@Override
	@Transactional
	public boolean applyJob(JobApplied jobapp) {
		try {
			jobapp.setJob_applied_id(this.nextJobAppliedID());
			sessionFactory.getCurrentSession().save(jobapp);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	@Transactional
	public String nextJobAppliedID() {
		String newID;
		List templist = sessionFactory.getCurrentSession().createQuery("from JobApplied order by job_applied_id desc").list();
		if(templist.size()==0)
		{
			newID="JA-001";	
		}
		else
		{
		JobApplied Obj = (JobApplied) templist.get(0);
		String id = Obj.getJob_applied_id();
		String temp = id.substring(0, 3);
		int tempID = Integer.parseInt(id.substring(3, 6));
		tempID++;
		newID = temp + String.format("%03d", tempID);
		}
		return newID;
	}

	@Override
	public boolean hasUserAppliedForTheJob(String username, String job_id) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JobApplied.class);
		criteria.add(Restrictions.eq("job_id", job_id));
		criteria.add(Restrictions.eq("username", username));
		
		List<JobApplied> liaj = criteria.list();
		if(liaj.isEmpty())
		{
			return true;
		}	
		else
		{
			return false;
		}
	}
}
