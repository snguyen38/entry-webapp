package com.entry.wepapp.dao;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.entry.wepapp.dao.blogpost.BlogPostDao;
import com.entry.wepapp.dao.blogpost.CategoryDao;
import com.entry.wepapp.dao.blogpost.CommentDao;
import com.entry.wepapp.dao.blogpost.PostDao;
import com.entry.wepapp.dao.user.UserDao;
import com.entry.wepapp.entity.BlogPost;
import com.entry.wepapp.entity.Category;
import com.entry.wepapp.entity.Comment;
import com.entry.wepapp.entity.Post;
import com.entry.wepapp.entity.Role;
import com.entry.wepapp.entity.User;

/**
 * Initialize the database with some test entries.
 *
 */
public class DataBaseInitializer
{
	private PasswordEncoder passwordEncoder;
	
    private BlogPostDao blogPostDao;
    
    private CategoryDao categoryDao;
    
    private PostDao postDao;

    private UserDao userDao;
    
    private CommentDao commentDao;
    

    protected DataBaseInitializer()
    {
        /* Default constructor for reflection instantiation */
    }

    public DataBaseInitializer(PasswordEncoder passwordEncoder, UserDao userDao, BlogPostDao blogPostDao, 
    		CategoryDao categoryDao, PostDao postDao, CommentDao commentDao)
    {
    	this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.blogPostDao = blogPostDao;
        this.categoryDao = categoryDao;
        this.postDao = postDao;
        this.commentDao = commentDao;
        
    }

    public void initDataBase()
    {
        User userUser = new User("admin", "admin", this.passwordEncoder.encode("admin"),
        		"admin@admin.com", "123456789", "Vietnam", "admin", "");
        userUser.addRole(Role.ADMIN);
        this.userDao.save(userUser);

        Category category1 = new Category("People");
        Category category2 = new Category("Building");
        Category category3 = new Category("Landscape");
        Category category4 = new Category("Nature");
        this.categoryDao.save(category1);
        this.categoryDao.save(category2);
        this.categoryDao.save(category3);
        this.categoryDao.save(category4);
        
        User userTemp = this.userDao.findAll().get(0);
        
        Post post1 = new Post("description 1", 0L, 0l, userTemp.getId(), category1.getCategoryName(), "images/stock/stock1.jpg");
        Post post2 = new Post("description 2", 0L, 0l, userTemp.getId(), category1.getCategoryName(), "images/stock/stock2.jpg");
        Post post3 = new Post("description 3", 0L, 0l, userTemp.getId(), category1.getCategoryName(), "images/stock/stock3.png");
        Post post4 = new Post("description 4", 0L, 0l, userTemp.getId(), category1.getCategoryName(), "images/stock/stock4.jpg");
        Post post5 = new Post("description 5", 0L, 0l, userTemp.getId(), category1.getCategoryName(), "images/stock/stock5.jpg");
        Post post6 = new Post("description 6", 0L, 0l, userTemp.getId(), category1.getCategoryName(), "images/stock/stock6.jpg");
        Post post7 = new Post("description 7", 0L, 0l, userTemp.getId(), category1.getCategoryName(), "images/stock/stock7.jpg");
        this.postDao.save(post1);
        this.postDao.save(post2);
        this.postDao.save(post3);
        this.postDao.save(post4);
        this.postDao.save(post5);
        this.postDao.save(post6);
        this.postDao.save(post7);
        
        System.out.println(this.postDao.findAll().get(0).getDescription());
        System.out.println(this.categoryDao.findAll().get(0).getCategoryName());
        
        Post postTemp = this.postDao.findAll().get(0);
        Post postTemp2 = this.postDao.findAll().get(1);
        Comment comment1 = new Comment(userUser.getNickName(), postTemp.getId(), "content1");
        Comment comment2 = new Comment(userUser.getNickName(), postTemp.getId(), "content2");
        Comment comment3 = new Comment(userUser.getNickName(), postTemp.getId(), "content3");
        Comment comment4 = new Comment(userUser.getNickName(), postTemp2.getId(), "content41");
        this.commentDao.save(comment1);
        this.commentDao.save(comment2);
        this.commentDao.save(comment3);
        this.commentDao.save(comment4);
        
        System.out.println(this.commentDao.findByPost(postTemp.getId()).get(0).getContent());
        System.out.println(this.commentDao.findByPost(postTemp.getId()).size());

        long timestamp = System.currentTimeMillis() - (1000 * 60 * 60 * 24);
        for (int i = 0; i < 10; i++) {
            BlogPost blogPost = new BlogPost();
            blogPost.setContent("This is example content " + i);
            blogPost.setDate(new Date(timestamp));
            this.blogPostDao.save(blogPost);
            timestamp += 1000 * 60 * 60;
        }
    }
}
