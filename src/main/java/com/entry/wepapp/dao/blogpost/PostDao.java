package com.entry.wepapp.dao.blogpost;

import com.entry.wepapp.dao.Dao;
import com.entry.wepapp.entity.BlogPost;
import com.entry.wepapp.entity.Post;

/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link BlogPost}s.
 *
 */
public interface PostDao extends Dao<Post, Long>
{
}
