package com.niit.hive.dao;

import java.util.List;

import com.niit.hive.model.Blog;
import com.niit.hive.model.BlogComment;

public interface BlogDAO {

		public boolean addBlog(Blog blog);
		public boolean addBlogComment(BlogComment blogcomment);
		public boolean updateBlog(Blog blog);
		public Blog getBlog(String blog_id);
		public List listBlogs();
		public List listBlogsByUser(String username);
		public List listAcceptedBlogs();
		public List listBlogCommentsByBlog(String blog_id);
		public boolean updateBlogStatus(String blog_id, String status, String reason);
		public String nextBlogID();
		public String nextBlogCommentID();
}
