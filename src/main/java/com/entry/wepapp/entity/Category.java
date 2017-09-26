package com.entry.wepapp.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SuppressWarnings("serial")
@javax.persistence.Entity
public class Category implements Entity
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 40, nullable = false)
    private String categoryName;

    protected Category()
    {
        /* Reflection instantiation */
    }

    public Category(String categoryName)
    {
        this.categoryName = categoryName;
    }

    @Override
    public Long getId()
    {
        return this.id;
    }

    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
