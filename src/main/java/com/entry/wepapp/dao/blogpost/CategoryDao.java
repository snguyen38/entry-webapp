package com.entry.wepapp.dao.blogpost;

import com.entry.wepapp.dao.Dao;
import com.entry.wepapp.entity.BlogPost;
import com.entry.wepapp.entity.Category;

/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link BlogPost}s.
 *
 */
public interface CategoryDao extends Dao<Category, Long>
{
}
