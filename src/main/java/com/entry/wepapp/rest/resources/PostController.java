package com.entry.wepapp.rest.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.entry.wepapp.dao.blogpost.CommentDao;
import com.entry.wepapp.dao.blogpost.PostDao;
import com.entry.wepapp.entity.Comment;
import com.entry.wepapp.entity.Post;
import com.entry.wepapp.entity.User;
import com.entry.wepapp.service.UserService;

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
    private UserService userService;
    
    @Context
	private ServletContext context;

    @POST
	@Path("/uploadImage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean registerUser(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, 
			@FormDataParam("userId") Long userId,
			@FormDataParam("category") String category,
			@FormDataParam("description") String description) {
		try {
			String fileContext = context.getRealPath("");
			String filePath = "images-storage\\posts\\" + category;
			if (!ObjectUtils.isEmpty(fileDetail.getFileName())) {
				filePath = filePath + "\\" + fileDetail.getFileName();
				File targetFile = new File(fileContext + filePath);
			    FileUtils.copyInputStreamToFile(uploadedInputStream, targetFile);
			}
			
			Post post = new Post(description, 0L, 0L, userId, category, filePath);
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Map read(@PathParam("id") Long id)
    {
    	Map res = new HashMap();
        this.LOGGER.info("read(id)");

        Post post = this.postDao.find(id);
        if (post == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        List<Comment> comments = this.commentDao.findByPost(id);
        
        res.put("post", post);
        res.put("comments", comments);
        
        User user = this.userService.findUserById(post.getUserId());
        res.put("fullName", user.getFirstName() + " " + user.getLastName());

        return res;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Post create(Post Post)
    {
        this.LOGGER.info("create(): " + Post);

        return this.postDao.save(Post);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Post update(@PathParam("id") Long id, Post Post)
    {
        this.LOGGER.info("update(): " + Post);

        return this.postDao.save(Post);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void delete(@PathParam("id") Long id)
    {
        this.LOGGER.info("delete(id)");

        this.postDao.delete(id);
    }

}
