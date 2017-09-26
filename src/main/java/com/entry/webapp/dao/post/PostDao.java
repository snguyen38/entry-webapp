package com.entry.webapp.dao.post;

import java.util.List;

import com.entry.webapp.dao.Dao;
import com.entry.webapp.entity.Post;

/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link PostDao}.
 *
 */
public interface PostDao extends Dao<Post, Long>
{
	 public boolean updateLikeAndDislikeNumber(long postId, long likeNumber, long dislikeNumber);
	 public List<Post> findPostByUser(String username);
}
