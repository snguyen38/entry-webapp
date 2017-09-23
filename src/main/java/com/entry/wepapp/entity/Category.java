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

    @Column(nullable = false)
    private String categoryName;

//    @OneToMany(fetch = FetchType.EAGER)
//	private Set<Post> posts = new HashSet<Post>();
    
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

	/*public Set<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public void addPosts(Post posts) {
		this.posts.add(posts);
	}*/

}
