package com.entry.webapp.rest.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entry.webapp.dao.post.CountryDao;
import com.entry.webapp.entity.Country;

@Component
@Path("/countries")
public class CountryController
{
	Logger LOGGER = LogManager.getRootLogger();

    
    @Autowired
    private CountryDao countryDao;
    
	@POST
	@Path("/getCountries")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Country> getCountries() {
		return this.countryDao.findAll();
	}
    
}
