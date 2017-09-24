package com.entry.wepapp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SuppressWarnings("serial")
@javax.persistence.Entity
public class Post implements Entity
{
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String description;
    
    @Column
    private Long likeNumber;
    
    @Column
    private Long dislikeNumber;
    
    @Column
    private String username;
    
    @Column
	private String category;
    
    @Column
    private String link;
    
    @Column
    private Date date;
    
    protected Post()
    {
        /* Reflection instantiation */
    }

    public Post(String description, Long likeNumber, Long dislikeNumber, String username, String category, String link) {
		this.description = description;
		this.likeNumber = likeNumber;
		this.dislikeNumber = dislikeNumber;
		this.username = username;
		this.category = category;
		this.link = link;
		this.date = new Date();
	}

    @Override
    public Long getId()
    {
        return this.id;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(Long likeNumber) {
		this.likeNumber = likeNumber;
	}

	public Long getDislikeNumber() {
		return dislikeNumber;
	}

	public void setDislikeNumber(Long dislikeNumber) {
		this.dislikeNumber = dislikeNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
