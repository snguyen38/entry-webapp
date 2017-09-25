package com.entry.wepapp.dao.post;

import java.util.List;

import com.entry.wepapp.dao.Dao;
import com.entry.wepapp.entity.Post;

/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link PostDao}s.
 *
 */
public interface PostDao extends Dao<Post, Long>
{
	 public boolean updateLikeAndDislikeNumber(long postId, long likeNumber, long dislikeNumber);
	 public List<Post> findPostByUser(String username);
}
