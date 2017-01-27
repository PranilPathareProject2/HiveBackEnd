package com.niit.hive.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.hive.dao.BlogDAO;
import com.niit.hive.model.Blog;
import com.niit.hive.model.BlogComment;

@Repository("blogDAO")
public class BlogDAOImpl implements BlogDAO {

	@Autowired
	public SessionFactory sessionFactory;
	
	public BlogDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean addBlog(Blog blog) {
		try {
			blog.setBlog_id(this.nextBlogID());
			sessionFactory.getCurrentSession().save(blog);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateBlog(Blog blog) {
		try {
			sessionFactory.getCurrentSession().update(blog);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public Blog getBlog(String blog_id) {
		Blog blog = sessionFactory.getCurrentSession().get(Blog.class, blog_id);
		return blog;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public List listBlogs() {
		List lib = sessionFactory.getCurrentSession().createQuery("from Blog").list();
		return lib;
	}

	@Override
	@Transactional
	public List listAcceptedBlogs() {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Blog.class);
		criteria.add(Restrictions.eq("status", "Accepted"));
		
		List liab = criteria.list();
		
		return liab;
	}

	@Override
	@Transactional
	public boolean updateBlogStatus(String blog_id, String status, String reason) {
		
		try {
			Blog blog = this.getBlog(blog_id);
			blog.setStatus(status);
			blog.setRejection_reason(reason);
			if(this.updateBlog(blog))
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
	public String nextBlogID() {
		String newID;
		List templist = sessionFactory.getCurrentSession().createQuery("from Blog order by blog_id desc").list();
		if(templist.size()==0)
		{
			newID="B-001";	
		}
		else
		{
			Blog Obj = (Blog) templist.get(0);
			String id = Obj.getBlog_id();
			String temp = id.substring(0, 2);
			int tempID = Integer.parseInt(id.substring(2, 5));
			tempID++;
			newID = temp + String.format("%03d", tempID);
		}
		return newID;
	}

	@Override
	@Transactional
	public String nextBlogCommentID() {
		String newID;
		List templist = sessionFactory.getCurrentSession().createQuery("from BlogComment order by blog_comment_id desc").list();
		if(templist.size()==0)
		{
			newID="BLC-001";	
		}
		else
		{
			BlogComment Obj = (BlogComment) templist.get(0);
			String id = Obj.getBlog_comment_id();
			String temp = id.substring(0, 4);
			int tempID = Integer.parseInt(id.substring(4, 7));
			tempID++;
			newID = temp + String.format("%03d", tempID);
		}
		return newID;
	}
	
	@Override
	@Transactional
	public boolean addBlogComment(BlogComment blogcomment) {
		try {
			blogcomment.setBlog_comment_id(this.nextBlogCommentID());;
			sessionFactory.getCurrentSession().save(blogcomment);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public List listBlogsByUser(String username) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Blog.class);
		criteria.add(Restrictions.eq("created_by", username));
		
		List libbu = criteria.list();
		
		return libbu;
	}

	@Override
	@Transactional
	public List listBlogCommentsByBlog(String blog_id) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BlogComment.class);
		criteria.add(Restrictions.eq("blog_id", blog_id));
		
		List libcbb = criteria.list();
		
		return libcbb;
	}
}
