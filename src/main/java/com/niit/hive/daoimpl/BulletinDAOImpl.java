package com.niit.hive.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.hive.dao.BulletinDAO;
import com.niit.hive.model.Bulletin;

@Repository("bulletinDAO")
public class BulletinDAOImpl implements BulletinDAO {

	@Autowired
	public SessionFactory sessionFactory;
	
	public BulletinDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean addBulletin(Bulletin bulletin) {
		try {
			bulletin.setBulletin_id(this.nextBulletinID());
			sessionFactory.getCurrentSession().save(bulletin);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateBulletin(Bulletin bulletin) {
		try {
			if(bulletin.getStatus() == null)
			{
				bulletin.setStatus("Activated");
			}
			sessionFactory.getCurrentSession().update(bulletin);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public Bulletin getBulletin(String bulletin_id) {
		Bulletin bulletin = sessionFactory.getCurrentSession().get(Bulletin.class, bulletin_id);
		return bulletin;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public List listBulletins() {
		List lib = sessionFactory.getCurrentSession().createQuery("from Bulletin").list();
		return lib;
	}

	@Override
	@Transactional
	public List listActiveBulletins() {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bulletin.class);
		criteria.add(Restrictions.eq("status", "Activated"));
		
		List liab = criteria.list();
		
		return liab;
	}

	@Override
	@Transactional
	public boolean updateBulletinStatus(String bulletin_id, String status) {
		
		try {
			Bulletin bulletin = this.getBulletin(bulletin_id);
			bulletin.setStatus(status);
			if(this.updateBulletin(bulletin))
			{	
				return true;
			}	
			else
			{	
				return false;
			}	
		} catch (Exception e) {
			return false;
		}		
	}
	
	@Override
	@Transactional
	public String nextBulletinID() {
		String newID;
		List templist = sessionFactory.getCurrentSession().createQuery("from Bulletin order by bulletin_id desc").list();
		if(templist.size()==0)
		{
			newID="BL-001";	
		}
		else
		{
			Bulletin Obj = (Bulletin) templist.get(0);
			String id = Obj.getBulletin_id();
			String temp = id.substring(0, 3);
			int tempID = Integer.parseInt(id.substring(3, 6));
			tempID++;
			newID = temp + String.format("%03d", tempID);
		}
		return newID;
	}
}
