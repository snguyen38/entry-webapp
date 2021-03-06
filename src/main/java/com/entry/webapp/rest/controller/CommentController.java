package com.entry.webapp.rest.controller;

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

import com.entry.webapp.dao.post.CommentDao;
import com.entry.webapp.entity.Comment;
import com.entry.webapp.service.UserService;
import com.entry.webapp.util.AppUtils;

@Component
@Path("/comments")
public class CommentController
{
	Logger LOGGER = LogManager.getRootLogger();

    @Autowired
    private CommentDao commentDao;
    
    @Autowired
    private UserService userService;
    
    @Context
	private ServletContext context;

    @POST
	@Path("/postComment")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean postComment(@FormDataParam("postId") Long postId,
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
        List<Comment> comments = this.commentDao.findByPost(postId);
     
        // get avatar for comments
        for (Comment comment:comments) {
        	String avatar = AppUtils.getValidAvatarDirectory(context.getRealPath(""),
        			this.userService.findUserByNickName(comment.getUsername()).getAvatar());
        	comment.setAvatar(avatar);
        }

        return comments;
    }

}
