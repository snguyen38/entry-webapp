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
    	
        User userAdmin = new User("admin", "admin", this.passwordEncoder.encode("admin"),
        		"admin@admin.com", "123456789", country1.getCountryName(), "admin", "images/avatar/admin.jpg");
        userAdmin.addRole(Role.ADMIN);
        this.userDao.save(userAdmin);
        
        User userMember = new User("member", "member", this.passwordEncoder.encode("member"),
        		"member@member.com", "123456789", country2.getCountryName(), "member", "images/avatar/member.jpg");
        userMember.addRole(Role.ADMIN);
        this.userDao.save(userMember);

        Category categoryPeople = new Category("People");
        Category categoryBuilding = new Category("Building");
        Category categoryOcean = new Category("Ocean");
        Category categoryNature = new Category("Nature");
        Category categoryAnimal = new Category("Animal");
        this.categoryDao.save(categoryPeople);
        this.categoryDao.save(categoryBuilding);
        this.categoryDao.save(categoryOcean);
        this.categoryDao.save(categoryNature);
        this.categoryDao.save(categoryAnimal);
        
        User userAdminTemp = this.userDao.findAll().get(0);
        User userMemberTemp = this.userDao.findAll().get(1);

        // People
        Post postPeople1 = new Post("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.", 10L, 2l, userAdminTemp.getUsername(), categoryPeople.getCategoryName(), "images/stock/people/people1.jpg");
        Post postPeople2 = new Post("Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.", 15L, 4l, userAdminTemp.getUsername(), categoryPeople.getCategoryName(), "images/stock/people/people2.jpg");
        Post postPeople3 = new Post("Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.", 43L, 1l, userMemberTemp.getUsername(), categoryPeople.getCategoryName(), "images/stock/people/people3.jpg");
        Post postPeople4 = new Post("Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. ", 77L, 4l, userAdminTemp.getUsername(), categoryPeople.getCategoryName(), "images/stock/people/people4.jpg");
        Post postPeople5 = new Post("Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna.", 3L, 9l, userMemberTemp.getUsername(), categoryPeople.getCategoryName(), "images/stock/people/people5.jpg");
        
        // Building
        Post postBuilding1 = new Post("Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.", 60L, 5l, userAdminTemp.getUsername(), categoryBuilding.getCategoryName(), "images/stock/building/building1.jpg");
        Post postBuilding2 = new Post("Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.", 21L, 3l, userMemberTemp.getUsername(), categoryBuilding.getCategoryName(), "images/stock/building/building2.jpg");        
        Post postBuilding3 = new Post("Sed fringilla mauris sit amet nibh.", 31L, 0l, userMemberTemp.getUsername(), categoryBuilding.getCategoryName(), "images/stock/building/building3.jpg");        
        Post postBuilding4 = new Post("Phasellus viverra nulla ut metus varius laoreet.", 13L, 34l, userMemberTemp.getUsername(), categoryBuilding.getCategoryName(), "images/stock/building/building4.jpg");        
        Post postBuilding5 = new Post("Donec pede justo. Sed fringilla mauris sit amet nibh.", 19L, 1l, userMemberTemp.getUsername(), categoryBuilding.getCategoryName(), "images/stock/building/building5.jpg");        
        
        // Ocean
        Post postOcean1 = new Post("Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.", 60L, 5l, userAdminTemp.getUsername(), categoryOcean.getCategoryName(), "images/stock/ocean/ocean1.jpg");
        Post postOcean2 = new Post("Donec pede justo. Sed fringilla mauris sit amet nibh.", 22L, 33l, userMemberTemp.getUsername(), categoryOcean.getCategoryName(), "images/stock/ocean/ocean2.jpg");        
        Post postOcean3 = new Post("Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.", 21L, 3l, userMemberTemp.getUsername(), categoryOcean.getCategoryName(), "images/stock/ocean/ocean3.jpg");        
        Post postOcean4 = new Post("Phasellus viverra nulla ut metus varius laoreet.", 121L, 3l, userMemberTemp.getUsername(), categoryOcean.getCategoryName(), "images/stock/ocean/ocean4.jpg");        
        Post postOcean5 = new Post("Sed fringilla mauris sit amet nibh.", 54L, 13l, userMemberTemp.getUsername(), categoryOcean.getCategoryName(), "images/stock/ocean/ocean5.jpg");        

        // Nature
        Post postNature1 = new Post("Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.", 60L, 5l, userAdminTemp.getUsername(), categoryNature.getCategoryName(), "images/stock/nature/nature1.jpg");
        Post postNature2 = new Post("Phasellus viverra nulla ut metus varius laoreet.", 28L, 1l, userMemberTemp.getUsername(), categoryNature.getCategoryName(), "images/stock/nature/nature2.jpg");        
        Post postNature3 = new Post("Sed fringilla mauris sit amet nibh.", 29L, 8l, userMemberTemp.getUsername(), categoryNature.getCategoryName(), "images/stock/nature/nature3.jpg");        
        Post postNature4 = new Post("Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.", 21L, 3l, userMemberTemp.getUsername(), categoryNature.getCategoryName(), "images/stock/nature/nature4.jpg");        
        Post postNature5 = new Post("Donec pede justo. Sed fringilla mauris sit amet nibh.", 71L, 36l, userMemberTemp.getUsername(), categoryNature.getCategoryName(), "images/stock/nature/nature5.jpg");        

        // Animal
        Post postAnimal1 = new Post("Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.", 60L, 5l, userAdminTemp.getUsername(), categoryAnimal.getCategoryName(), "images/stock/animal/animal1.jpg");
        Post postAnimal2 = new Post("Sed fringilla mauris sit amet nibh.", 2L, 3l, userMemberTemp.getUsername(), categoryAnimal.getCategoryName(), "images/stock/animal/animal2.jpg");        
        Post postAnimal3 = new Post("Donec pede justo, fringilla vel" , 1L, 3l, userMemberTemp.getUsername(), categoryAnimal.getCategoryName(), "images/stock/animal/animal3.jpg");        
        Post postAnimal4 = new Post("Sed fringilla mauris sit amet nibh." , 51L, 13l, userMemberTemp.getUsername(), categoryAnimal.getCategoryName(), "images/stock/animal/animal4.jpg");        
        Post postAnimal5 = new Post("Sed fringilla mauris sit amet nibh." , 29L, 33l, userMemberTemp.getUsername(), categoryAnimal.getCategoryName(), "images/stock/animal/animal5.jpg");        

        // mix-up insertion
        this.postDao.save(postNature3);		this.postDao.save(postPeople1);
        this.postDao.save(postOcean2);		this.postDao.save(postAnimal2);
        this.postDao.save(postNature2);		this.postDao.save(postPeople2);
        this.postDao.save(postPeople3);		this.postDao.save(postOcean5);
        this.postDao.save(postPeople4);		this.postDao.save(postNature5);
        this.postDao.save(postPeople5);		this.postDao.save(postOcean4);
        this.postDao.save(postAnimal4);		this.postDao.save(postBuilding1);
        this.postDao.save(postNature4);		this.postDao.save(postBuilding2);
        this.postDao.save(postOcean3);		this.postDao.save(postOcean1);
        this.postDao.save(postNature1);		this.postDao.save(postAnimal3);
        this.postDao.save(postBuilding3);	this.postDao.save(postAnimal1);
        this.postDao.save(postBuilding4);	this.postDao.save(postAnimal5);
        this.postDao.save(postBuilding5);
        
        
        System.out.println(this.postDao.findAll().get(0).getDescription());
        System.out.println(this.categoryDao.findAll().get(0).getCategoryName());
        
        Post postTemp1 = this.postDao.findAll().get(0);
        Post postTemp2 = this.postDao.findAll().get(1);
        Post postTemp3 = this.postDao.findAll().get(2);
        Post postTemp4 = this.postDao.findAll().get(3);
        Post postTemp5 = this.postDao.findAll().get(4);
        Post postTemp6 = this.postDao.findAll().get(5);
        Post postTemp7 = this.postDao.findAll().get(6);
        Post postTemp8 = this.postDao.findAll().get(7);
        Post postTemp9 = this.postDao.findAll().get(8);
        Post postTemp10 = this.postDao.findAll().get(9);
        Comment comment1 = new Comment(userAdmin.getNickName(), postTemp1.getId(), "Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.");
        Comment comment2 = new Comment(userMember.getNickName(), postTemp1.getId(), "Donec pede justo, fringilla vel");
        Comment comment3 = new Comment(userAdmin.getNickName(), postTemp1.getId(), "Donec pede justo, fringilla ve");
        
        Comment comment4 = new Comment(userAdmin.getNickName(), postTemp2.getId(), "Donec pede justo, fringilla vel, fringilla vel");
        Comment comment5 = new Comment(userMember.getNickName(), postTemp2.getId(), "Sed fringilla mauris sit");
        
        Comment comment6 = new Comment(userAdmin.getNickName(), postTemp3.getId(), "Donec pede justo, fringilla ve");
        Comment comment7 = new Comment(userAdmin.getNickName(), postTemp3.getId(), "Donec pede lla vel, fringilla vel");
        Comment comment8 = new Comment(userMember.getNickName(), postTemp3.getId(), "Sed fringilla mauris sit");
        
        Comment comment9 = new Comment(userAdmin.getNickName(), postTemp4.getId(), "Donec pede justo, fringilla ve");
        Comment comment10 = new Comment(userMember.getNickName(), postTemp4.getId(), "Donec  fringilla vel, fringilla vel");
        Comment comment11 = new Comment(userAdmin.getNickName(), postTemp4.getId(), "Sed fringilla mauris sit");
        
        Comment comment12 = new Comment(userMember.getNickName(), postTemp5.getId(), "Donec pede gilla ve");
        Comment comment13 = new Comment(userAdmin.getNickName(), postTemp5.getId(), "Donec pede justo, fringilla vell");
        Comment comment14 = new Comment(userMember.getNickName(), postTemp5.getId(), "Sed fringilla mauris sit");
        
        Comment comment15 = new Comment(userAdmin.getNickName(), postTemp6.getId(), "Donec pede gilla ve");
        Comment comment16 = new Comment(userAdmin.getNickName(), postTemp6.getId(), "Donec pede justo, fringilla vell");
        Comment comment17 = new Comment(userMember.getNickName(), postTemp6.getId(), "Sed fringilla mauris sit");
        
        Comment comment18 = new Comment(userAdmin.getNickName(), postTemp7.getId(), "Donec pede gilla ve");
        Comment comment19 = new Comment(userMember.getNickName(), postTemp7.getId(), "Donec pede justo, fringilla vell");
        Comment comment20 = new Comment(userMember.getNickName(), postTemp7.getId(), "Sed fringilla mauris sit");
        
        Comment comment21 = new Comment(userAdmin.getNickName(), postTemp8.getId(), "Donec pede gilla ve");
        Comment comment22 = new Comment(userAdmin.getNickName(), postTemp8.getId(), "Donec pede justo, fringilla vell");
        Comment comment23 = new Comment(userMember.getNickName(), postTemp8.getId(), "Sed fringilla mauris sit");
        
        Comment comment24 = new Comment(userMember.getNickName(), postTemp9.getId(), "Donec pede gilla ve");
        Comment comment25 = new Comment(userAdmin.getNickName(), postTemp9.getId(), "Donec pede justo, fringilla vell");
        Comment comment26 = new Comment(userMember.getNickName(), postTemp9.getId(), "Sed fringilla mauris sit");
        
        Comment comment27 = new Comment(userAdmin.getNickName(), postTemp10.getId(), "Donec pede gilla ve");
        Comment comment28 = new Comment(userAdmin.getNickName(), postTemp10.getId(), "Donec pede justo, fringilla vell");
        Comment comment29 = new Comment(userMember.getNickName(), postTemp10.getId(), "Sed fringilla mauris sit");
        Comment comment30 = new Comment(userMember.getNickName(), postTemp10.getId(), "Sed fringilla mauris sit");
        
        this.commentDao.save(comment1);        this.commentDao.save(comment2);
        this.commentDao.save(comment3);        this.commentDao.save(comment4);
        this.commentDao.save(comment5);        this.commentDao.save(comment6);
        this.commentDao.save(comment7);        this.commentDao.save(comment8);
        this.commentDao.save(comment9);        this.commentDao.save(comment10);
        this.commentDao.save(comment11);       this.commentDao.save(comment12);
        this.commentDao.save(comment13);       this.commentDao.save(comment14);
        this.commentDao.save(comment15);       this.commentDao.save(comment16);
        this.commentDao.save(comment17);       this.commentDao.save(comment18);
        this.commentDao.save(comment19);       this.commentDao.save(comment20);
        this.commentDao.save(comment21);       this.commentDao.save(comment22);
        this.commentDao.save(comment23);       this.commentDao.save(comment24);
        this.commentDao.save(comment25);       this.commentDao.save(comment26);
        this.commentDao.save(comment27);       this.commentDao.save(comment28);
        this.commentDao.save(comment29);       this.commentDao.save(comment30);

    }
}
