package com.entry.wepapp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@javax.persistence.Entity
public class Comment implements Entity
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private Long postId;
    
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false)
    private Date date;
    
    @Transient
    private String avatar;

    
    protected Comment()
    {
        /* Reflection instantiation */
    }

    public Comment(String username, Long postId, String content)
    {
        this.username = username;
        this.postId = postId;
        this.content = content;
        this.date = new Date();
    }

    @Override
    public Long getId()
    {
        return this.id;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPost(Long postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
