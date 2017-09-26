package com.entry.wepapp.dao;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.entry.wepapp.dao.post.CategoryDao;
import com.entry.wepapp.dao.post.CommentDao;
import com.entry.wepapp.dao.post.CountryDao;
import com.entry.wepapp.dao.post.PostDao;
import com.entry.wepapp.dao.user.UserDao;
import com.entry.wepapp.entity.Category;
import com.entry.wepapp.entity.Comment;
import com.entry.wepapp.entity.Country;
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
	
    private CategoryDao categoryDao;
    
    private PostDao postDao;

    private UserDao userDao;
    
    private CommentDao commentDao;
    
    private CountryDao countryDao;
    

    protected DataBaseInitializer()
    {
        /* Default constructor for reflection instantiation */
    }

    public DataBaseInitializer(PasswordEncoder passwordEncoder, UserDao userDao,
    		CategoryDao categoryDao, PostDao postDao, CommentDao commentDao, CountryDao countryDao)
    {
    	this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.countryDao = countryDao;
    }

    public void initDataBase()
    {
    	Country country1 = new Country("Vietnam");
    	Country country2 = new Country("US");
    	Country country3 = new Country("UK");
    	Country country4 = new Country("Singapore");
    	Country country5 = new Country("Laos");
    	Country country6 = new Country("Campodia");
    	Country country7 = new Country("Thailand");
    	Country country8 = new Country("Indonesia");
        this.countryDao.save(country1);
        this.countryDao.save(country2);
        this.countryDao.save(country3);
        this.countryDao.save(country4);
        this.countryDao.save(country5);
        this.countryDao.save(country6);
        this.countryDao.save(country7);
        this.countryDao.save(country8);
    	
        User userUser = new User("admin", "admin", this.passwordEncoder.encode("admin"),
        		"admin@admin.com", "123456789", country1.getCountryName(), "admin", "");
        userUser.addRole(Role.ADMIN);
        this.userDao.save(userUser);

        Category category1 = new Category("People");
        Category category2 = new Category("Building");
        Category category3 = new Category("Landscape");
        Category category4 = new Category("Nature");
        Category category5 = new Category("Animal");
        this.categoryDao.save(category1);
        this.categoryDao.save(category2);
        this.categoryDao.save(category3);
        this.categoryDao.save(category4);
        this.categoryDao.save(category5);
        
        User userTemp = this.userDao.findAll().get(0);
        
        Post post1 = new Post("description 1", 10L, 2l, userTemp.getUsername(), category1.getCategoryName(), "images/stock/stock1.jpg");
        Post post2 = new Post("description 2", 15L, 4l, userTemp.getUsername(), category1.getCategoryName(), "images/stock/stock2.jpg");
        Post post3 = new Post("description 3", 43L, 1l, userTemp.getUsername(), category1.getCategoryName(), "images/stock/stock3.png");
        Post post4 = new Post("description 4", 77L, 4l, userTemp.getUsername(), category1.getCategoryName(), "images/stock/stock4.jpg");
        Post post5 = new Post("description 5", 3L, 9l, userTemp.getUsername(), category4.getCategoryName(), "images/stock/stock5.jpg");
        Post post6 = new Post("description 6", 60L, 5l, userTemp.getUsername(), category4.getCategoryName(), "images/stock/stock6.jpg");
        Post post7 = new Post("description 7", 21L, 3l, userTemp.getUsername(), category5.getCategoryName(), "images/stock/stock7.jpg");
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
        Comment comment1 = new Comment(userUser.getNickName(), postTemp.getId(), "comment 1");
        Comment comment2 = new Comment(userUser.getNickName(), postTemp.getId(), "comment 2");
        Comment comment3 = new Comment(userUser.getNickName(), postTemp.getId(), "comment 3");
        Comment comment4 = new Comment(userUser.getNickName(), postTemp2.getId(), "comment 1");
        Comment comment5 = new Comment(userUser.getNickName(), postTemp2.getId(), "comment 2");
        this.commentDao.save(comment1);
        this.commentDao.save(comment2);
        this.commentDao.save(comment3);
        this.commentDao.save(comment4);
        this.commentDao.save(comment5);

    }
}
