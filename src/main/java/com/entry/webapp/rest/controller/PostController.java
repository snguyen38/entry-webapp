package com.entry.webapp.rest.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.entry.webapp.dao.post.CategoryDao;
import com.entry.webapp.dao.post.CommentDao;
import com.entry.webapp.dao.post.PostDao;
import com.entry.webapp.entity.Category;
import com.entry.webapp.entity.Comment;
import com.entry.webapp.entity.Post;
import com.entry.webapp.entity.User;
import com.entry.webapp.service.UserService;
import com.entry.webapp.util.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Path("/posts")
public class PostController
{
	Logger LOGGER = LogManager.getRootLogger();

    @Autowired
    private PostDao postDao;
    
    @Autowired
    private CommentDao commentDao;
    
    @Autowired
    private CategoryDao categoryDao;
    
    @Autowired
    private UserService userService;
    
    @Context
	private ServletContext context;

    @POST
	@Path("/uploadImage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean registerUser(@FormDataParam("file") InputStream uploadedInputStream, FormDataMultiPart multipart,
			@FormDataParam("file") FormDataContentDisposition fileDetail, 
			@FormDataParam("username") String username,
			@FormDataParam("category") String category,
			@FormDataParam("description") String description) {
		try {
			String fileContext = context.getRealPath("");
			String filePath = "images-storage" + File.separator + "posts" + File.separator + category;
			if (!ObjectUtils.isEmpty(fileDetail.getFileName())) {
				filePath = filePath + File.separator + fileDetail.getFileName();
				File targetFile = new File(fileContext + filePath);
			    FileUtils.copyInputStreamToFile(uploadedInputStream, targetFile);
			    if (targetFile.length() > 5242880) {
			    	targetFile.delete();
			    	return false;
			    }
			}
			
			Post post = new Post(description, 0L, 0L, username, category, filePath);
			return !ObjectUtils.isEmpty(this.postDao.save(post));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> list() throws IOException
    {
        this.LOGGER.info("list()");
        List<Post> allEntries = this.postDao.findAll();

        return allEntries;
    }

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map read(@PathParam("id") Long id)
    {
    	Map res = new HashMap();
        this.LOGGER.info("read(id)");

        Post post = this.postDao.find(id);
        if (post == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        res.put("post", post);
        
        List<Comment> comments = this.commentDao.findByPost(id);
    
        // get avatar for comments
        for (Comment comment:comments) {
        	String avatar = AppUtils.getValidAvatarDirectory(context.getRealPath(""),
        			this.userService.findUserByNickName(comment.getUsername()).getAvatar());
        	comment.setAvatar(avatar);
        }
        
        res.put("comments", comments);
       
        User user = this.userService.findUserById(post.getId());
        if (!ObjectUtils.isEmpty(user)) {
        	res.put("username", user.getNickName());
        }

        return res;
    }

    @POST
	@Path("/getPostByUser")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getPostByUser(@FormDataParam("userId") long userId) {
    	List<Map> response = new ArrayList<>();
        List<Post> posts = this.postDao.findPostByUser(this.userService.findUserById(userId).getNickName());
        if (!CollectionUtils.isEmpty(posts)) {
            for (Post post:posts) {
            	Map map = new HashMap();
            	map.put("post", post);
                List<Comment> comments = this.commentDao.findByPost(post.getId());
                
             // get avatar for comments
                for (Comment comment:comments) {
                	String avatar = AppUtils.getValidAvatarDirectory(context.getRealPath(""),
                			this.userService.findUserByNickName(comment.getUsername()).getAvatar());
                	comment.setAvatar(avatar);
                }
                
                map.put("comments", comments);
                
                response.add(map);
            }
        }
        
        return response;
    }
    
    @POST
	@Path("/updateLikeAndDislike")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean registerUser(@FormDataParam("postId") long postId,
			@FormDataParam("likeNumber") long likeNumber,
			@FormDataParam("dislikeNumber") long dislikeNumber) {
		try {
			return this.postDao.updateLikeAndDislikeNumber(postId, likeNumber, dislikeNumber);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}
    
    
	@POST
	@Path("/deletePosts")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")
	public boolean deletePosts(@FormDataParam("postIds") String postIds) {
		try {
			ObjectMapper ob = new ObjectMapper();
			List<Integer> ids = ob.readValue(postIds, List.class);
			for (long id:ids) {
				this.postDao.delete(id);
			}
			
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}

	@POST
	@Path("/getCategories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategories() {
        return this.categoryDao.findAll();
    }

}
