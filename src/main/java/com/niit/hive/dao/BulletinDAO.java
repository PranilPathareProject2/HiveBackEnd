package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.Bulletin;

public interface BulletinDAO {

		public boolean addBulletin(Bulletin bulletin);
		public boolean updateBulletin(Bulletin bulletin);
		public Bulletin getBulletin(String bulletin_id);
		public List listBulletins();
		public List listActiveBulletins();
		public boolean updateBulletinStatus(String bulletin_id, String status);
		public String nextBulletinID();
}
