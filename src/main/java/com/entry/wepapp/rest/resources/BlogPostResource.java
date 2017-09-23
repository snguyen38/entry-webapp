package com.entry.wepapp.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entry.wepapp.dao.blogpost.BlogPostDao;
import com.entry.wepapp.entity.BlogPost;

@Component
@Path("/blogposts")
public class BlogPostResource
{
	Logger LOGGER = LogManager.getRootLogger();

    @Autowired
    private BlogPostDao blogPostDao;

//    @Autowired
//    private ObjectMapper mapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BlogPost> list() throws IOException
    {
        this.LOGGER.info("list()");

//        ObjectWriter viewWriter;
        /*if (this.isAdmin()) {
            viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
        } else {*/
//            viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
//        }
        List<BlogPost> allEntries = this.blogPostDao.findAll();

        return allEntries;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public BlogPost read(@PathParam("id") Long id)
    {
        this.LOGGER.info("read(id)");

        BlogPost blogPost = this.blogPostDao.find(id);
        if (blogPost == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return blogPost;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BlogPost create(BlogPost blogPost)
    {
        this.LOGGER.info("create(): " + blogPost);

        return this.blogPostDao.save(blogPost);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public BlogPost update(@PathParam("id") Long id, BlogPost blogPost)
    {
        this.LOGGER.info("update(): " + blogPost);

        return this.blogPostDao.save(blogPost);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void delete(@PathParam("id") Long id)
    {
        this.LOGGER.info("delete(id)");

        this.blogPostDao.delete(id);
    }

    /*private boolean isAdmin()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetails)) {
            return false;
        }

        UserDetails userDetails = (UserDetails) principal;

        return userDetails.getAuthorities().contains(Role.ADMIN);
    }*/
}
