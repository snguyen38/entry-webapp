package com.entry.wepapp.dao.blogpost;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.entry.wepapp.dao.JpaDao;
import com.entry.wepapp.entity.Post;

/**
 * JPA Implementation of a {@link PostDao}.
 *
 */
public class JpaPostDao extends JpaDao<Post, Long> implements PostDao
{
    public JpaPostDao()
    {
        super(Post.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAll()
    {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);

        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.orderBy(builder.desc(root.get("date")));

        TypedQuery<Post> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
