package com.niit.hive.controller;

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

import com.niit.hive.dao.BlogDAO;
import com.niit.hive.model.Blog;
import com.niit.hive.model.BlogComment;

@RestController
public class BlogController {

	@Autowired
	BlogDAO blogDAO;
	
	@Autowired
	Blog blog;
	
	@Autowired
	BlogComment blogComment;
	
	@Autowired
	HttpSession httpSession;
	
	@RequestMapping(value="/listblogs", method=RequestMethod.GET)
	public ResponseEntity<List<Blog>> listBlogs()
	{
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");

		List<Blog> blogs = new ArrayList<Blog>();
		if(loggedInUserRole == null)
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("You have to login to manage blogs");
			blogs.add(blog);
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
		}
		
		/*System.out.println("loggedInUserRole = "+loggedInUserRole);
		System.out.println("True or False = "+loggedInUserRole.equals("ROLE_ADMIN"));*/
		if(!loggedInUserRole.equals("ROLE_ADMIN"))
		{	
			blog.setErrorCode("404");
			blog.setErrorMessage("You are not authorized to manage blogs");
			blogs.add(blog);
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
		}
		else
		{	
			blogs = blogDAO.listBlogs();
			
			if(blogs.isEmpty())
			{
				blog.setErrorCode("404");
				blog.setErrorMessage("You have no blogs to manage");
				blogs.add(blog);
			}
		}
		
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	@RequestMapping(value="/acceptedblogs", method=RequestMethod.GET)
	public ResponseEntity<List<Blog>> acceptedBlogs()
	{
		List<Blog> blogs = blogDAO.listAcceptedBlogs();
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	@RequestMapping(value="/acceptblog/{blog_id}", method=RequestMethod.PUT)
	public ResponseEntity<Blog> acceptBlog(@PathVariable("blog_id") String blog_id)
	{
		blog = updateStatus(blog_id, "Accepted", "");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@RequestMapping(value="/rejectblog/{blog_id}/{reason}", method=RequestMethod.PUT)
	public ResponseEntity<Blog> rejectBlog(@PathVariable("blog_id") String blog_id, @PathVariable("reason") String reason)
	{
		blog = updateStatus(blog_id, "Rejected", reason);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}
	
	private Blog updateStatus(String blog_id, String status, String reason)
	{
		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");
		
		if(loggedInUserRole == null)
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("You have to login to update blog status");
			//return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		else if(!loggedInUserRole.equals("ROLE_ADMIN"))
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("You are not authorized to update blog status");
		}
		else
		{
			if(blogDAO.updateBlogStatus(blog_id, status, reason))
			{	
				blog.setErrorCode("200");
				blog.setErrorMessage("Blog status updated successfully");
			}
			else
			{
				blog.setErrorCode("404");
				blog.setErrorMessage("Blog status was not updated");
			}	
		}
		return blog;
	}
	
	@RequestMapping(value="/listblogsbyuser", method=RequestMethod.GET)
	public ResponseEntity<List<Blog>> listBlogsByUser()
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");

		List<Blog> blogs = new ArrayList<Blog>();
		if(loggedInUser == null)
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("You have to login to see your blogs");
			blogs.add(blog);
		}
		else
		{	
			blogs = blogDAO.listBlogsByUser(loggedInUser);
			
			if(blogs.isEmpty())
			{
				blog.setErrorCode("404");
				blog.setErrorMessage("You have not created any blog yet");
				blogs.add(blog);
			}
		}
		
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addblog", method=RequestMethod.POST)
	public ResponseEntity<Blog> addBlog(@RequestBody Blog blog)
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");

		if(loggedInUser == null)
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("You have to login to add a blog");
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		else
		{
			blog.setCreated_by(loggedInUser);
			if(blogDAO.addBlog(blog))
			{
				blog.setErrorCode("200");
				blog.setErrorMessage("Blog added successfully");
			}
			else
			{
				blog.setErrorCode("404");
				blog.setErrorMessage("Blog not added");
			}
		}
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateblog", method=RequestMethod.PUT)
	public ResponseEntity<Blog> updateBlog(@RequestBody Blog blog)
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");

		if(loggedInUser == null)
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("You have to login to update a blog");
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		else
		{
			if(this.isBlogByIDAndUsernameExists(blog.getBlog_id(), loggedInUser))
			{
				//blog.setCreated_by(loggedInUser);
				blogDAO.updateBlog(blog);
				blog.setErrorCode("200");
				blog.setErrorMessage("Blog updated successfully");
			}
			else
			{
				blog.setErrorCode("404");
				blog.setErrorMessage("Blog not updated, as it is not created by you");
			}
		}
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getblog/{blog_id}", method=RequestMethod.GET)
	public ResponseEntity<Blog> getBlogToUpdate(@PathVariable("blog_id") String blog_id)
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");
		
		if(loggedInUser == null)
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("You have to login to fetch a blog");
			//return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		else if(this.isBlogByIDAndUsernameExists(blog_id, loggedInUser))
		{
			blog = blogDAO.getBlog(blog_id);
			blog.setErrorCode("200");
			blog.setErrorMessage("The requested blog is fetched successfully");			
		}	
		else
		{
			blog.setErrorCode("404");
			blog.setErrorMessage("The requested blog is not created by you, so you cannot fetch it to update");
		}
		
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@RequestMapping(value="/getblogtodisplay/{blog_id}", method=RequestMethod.GET)
	public ResponseEntity<Blog> getBlogToDisplay(@PathVariable("blog_id") String blog_id)
	{
		blog = blogDAO.getBlog(blog_id);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}
	
	@RequestMapping(value="/listblogcommentsbyblog/{blog_id}", method=RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> listBlogCommentsByBlog(@PathVariable("blog_id") String blog_id)
	{
		List<BlogComment> blogcomments = blogDAO.listBlogCommentsByBlog(blog_id);
		return new ResponseEntity<List<BlogComment>>(blogcomments, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addblogcomment/{blog_id}", method=RequestMethod.POST)
	public ResponseEntity<BlogComment> addBlogComment(@RequestBody BlogComment blogcomment, @PathVariable("blog_id") String blog_id)
	{
		String loggedInUser = (String) httpSession.getAttribute("loggedInUser");

		if(loggedInUser == null)
		{
			blogcomment.setErrorCode("404");
			blogcomment.setErrorMessage("You have to login to add a blog comment");
			return new ResponseEntity<BlogComment>(blogcomment, HttpStatus.OK);
		}
		else
		{
			blogcomment.setBlog_id(blog_id);
			blogcomment.setComment_by(loggedInUser);
			if(blogDAO.addBlogComment(blogcomment))
			{
				blogcomment.setErrorCode("200");
				blogcomment.setErrorMessage("Blog comment added successfully");
			}
			else
			{
				blogcomment.setErrorCode("404");
				blogcomment.setErrorMessage("Blog comment not added");
			}
		}

		return new ResponseEntity<BlogComment>(blogcomment, HttpStatus.OK);
	}
	
	private boolean isBlogByIDAndUsernameExists(String blog_id, String username)
	{
		if(blogDAO.isBlogByIDAndUsernameExists(blog_id, username) != null)
			return true;
		else
			return false;
	}
}
