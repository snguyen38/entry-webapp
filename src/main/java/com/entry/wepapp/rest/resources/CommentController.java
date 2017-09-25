package com.entry.wepapp.rest.resources;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.entry.wepapp.dao.post.CommentDao;
import com.entry.wepapp.entity.Comment;

@Component
@Path("/comments")
public class CommentController
{
	Logger LOGGER = LogManager.getRootLogger();

//    @Autowired
//    private PostDao postDao;
    
    @Autowired
    private CommentDao commentDao;
    
//    @Autowired
//    private UserService userService;
    
    @Context
	private ServletContext context;

    @POST
	@Path("/postComment")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean registerUser(@FormDataParam("postId") Long postId,
			@FormDataParam("username") String username,
			@FormDataParam("content") String content) {
		try {
			Comment comment = new Comment(username, postId, content);
			return !ObjectUtils.isEmpty(this.commentDao.save(comment));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}

    @POST
    @Path("/getCommentsByPost")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
    public List<Comment> findByPost(@FormDataParam("postId") Long postId) {
        List<Comment> allEntries = this.commentDao.findByPost(postId);

        return allEntries;
    }

    /*@SuppressWarnings({ "rawtypes", "unchecked" })
	@GET
    @Produces(MediaType.APPLICATION_JSON)
//    @Path("{postId}/{postId}/{postId}")
    public Map read(@PathParam("id") Long id)
    {
    	Map res = new HashMap();

        Post post = this.postDao.find(id);
        if (post == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        List<Comment> comments = this.commentDao.findByPost(id);
        
        res.put("post", post);
        res.put("comments", comments);
        
        User user = this.userService.findUserById(post.getUserId());
        res.put("username", user.getNickName());

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
    }*/

}
