package com.entry.wepapp.rest.controller;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.entry.webapp.util.AppHelper;
import com.entry.webapp.util.AppUtils;
import com.entry.wepapp.entity.AccessToken;
import com.entry.wepapp.entity.Role;
import com.entry.wepapp.entity.User;
import com.entry.wepapp.service.UserService;
import com.entry.wepapp.transfer.UserTransfer;
@Component
@Path("/user")
public class UserController
{
	Logger LOGGER = LogManager.getRootLogger();
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private AppHelper appHelper;
    
    @Context
	private ServletContext context;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager;

    /**
     * Retrieves the currently logged in user.
     *
     * @return A transfer containing the username and the roles.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserTransfer getUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetails)) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        UserDetails userDetails = (UserDetails) principal;
        User user = this.userService.findUserByNickName(userDetails.getUsername());
        String avatarDir = AppUtils.getValidAvatarDirectory(context.getRealPath(""), user.getAvatar());
        return new UserTransfer(user.getId(), userDetails.getUsername(), avatarDir, this.createRoleMap(userDetails));
    }
    
    @POST
	@Path("/getUserById")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@FormDataParam("id") Long id)
    {
        return this.userService.findUserById(id);
    }

    /**
     * Authenticates a user and creates an access token.
     *
     * @param username The name of the user.
     * @param password The password of the user.
     * @return The generated access token.
     */
    @Path("authenticate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AccessToken authenticate(@FormParam("username") String username, @FormParam("password") String password)
    {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        return this.userService.createAccessToken((User) principal);
    }
    
    private Map<String, Boolean> createRoleMap(UserDetails userDetails)
    {
        Map<String, Boolean> roles = new HashMap<>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.put(authority.getAuthority(), Boolean.TRUE);
        }

        return roles;
    }
    
    @Path("getAvatar")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getPhoto(@QueryParam("username") String username) throws Exception {
		String fileContext = context.getRealPath("");   
		String filePath = this.userService.findUserByNickName(username).getAvatar();
		File file = new File(fileContext + filePath);
	    
		if (!file.isDirectory() && file.exists()) {
			return filePath;
		} else {
			return "images/avatar/default.png";
		}
	}
    
    @POST
	@Path("/register")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, 
			@FormDataParam("firstName") String firstName,
			@FormDataParam("lastName") String lastName,
			@FormDataParam("password") String password,
			@FormDataParam("email") String email,
			@FormDataParam("phone") String phone,
			@FormDataParam("country") String country,
			@FormDataParam("nickName") String nickName) throws Exception {
		try {
			Map<String, Boolean> res = appHelper.checkUserExist(email, nickName);
			if (!ObjectUtils.isEmpty(res)) {
				return Response.status(400).entity(res).build();
			}
			
			String fileContext = context.getRealPath("");   
			String filePath = "images-storage\\" + nickName;
			if (!ObjectUtils.isEmpty(fileDetail.getFileName())) {
				filePath = filePath + "\\" + fileDetail.getFileName();
				File targetFile = new File(fileContext.toLowerCase() + filePath.toLowerCase());
			    FileUtils.copyInputStreamToFile(uploadedInputStream, targetFile);
			}
			
			User user = new User(firstName, lastName, this.passwordEncoder.encode(password),
	        		email, phone, country, nickName, filePath.toLowerCase());
	        user.addRole(Role.ADMIN);
	        System.out.println("real path: " + filePath);
			if (!ObjectUtils.isEmpty(this.userService.saveUser(user))) {
				res.put("status", true); 
			}
				
			return Response.status(200).entity(res).build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception("c");
		}
	}
	
	
	@POST
	@Path("/updateUser/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("id") Long id,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, 
			@FormDataParam("firstName") String firstName,
			@FormDataParam("lastName") String lastName,
			@FormDataParam("password") String password,
			@FormDataParam("phone") String phone,
			@FormDataParam("country") String country,
			@FormDataParam("nickName") String nickName) {
		try {
			if (!this.userService.findUserById(id).getNickName().equals(nickName)) {
				Map<String, Boolean> res = appHelper.checkUserExist("", nickName);
				if (!ObjectUtils.isEmpty(res)) {
					return Response.status(400).entity(res).build();
				}
			}
			
			String fileContext = context.getRealPath("");
			String filePath = "";
			if (!ObjectUtils.isEmpty(fileDetail.getFileName())) {
				filePath = "images-storage\\" + nickName;
				filePath = filePath + "\\" + fileDetail.getFileName();
				File targetFile = new File(fileContext.toLowerCase() + filePath.toLowerCase());
			    FileUtils.copyInputStreamToFile(uploadedInputStream, targetFile);
			}

		    User user = new User(firstName, lastName, this.passwordEncoder.encode(password),
					userService.findUserById(id).getEmail(), phone, country, nickName, filePath.toLowerCase());
	        user.addRole(Role.ADMIN);
	        user.setId(id);
	        
	        User userResponse = this.userService.saveUser(user);
	        if (!ObjectUtils.isEmpty(userResponse)) {
	        	AppUtils.getValidAvatarDirectory(context.getRealPath(""), userResponse.getAvatar());
	        	return Response.status(200).entity(AppUtils.getValidAvatarDirectory(context.getRealPath(""), userResponse)).build();
	        }
	        return Response.status(500).entity(null).build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}
}
