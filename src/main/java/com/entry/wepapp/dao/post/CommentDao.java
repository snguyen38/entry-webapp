package com.entry.wepapp.dao.post;

import java.util.List;

import com.entry.wepapp.dao.Dao;
import com.entry.wepapp.entity.Comment;

/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link Comment}s.
 *
 */
public interface CommentDao extends Dao<Comment, Long>
{
	public List<Comment> findByPost(Long postId);
}
