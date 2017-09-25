package com.entry.wepapp.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SuppressWarnings("serial")
@javax.persistence.Entity
public class Country implements Entity
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String countryName;

    protected Country()
    {
        /* Reflection instantiation */
    }

    public Country(String countryName)
    {
        this.countryName = countryName;
    }

    @Override
    public Long getId()
    {
        return this.id;
    }

    public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
