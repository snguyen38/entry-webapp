package com.entry.wepapp.dao.blogpost;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.entry.wepapp.dao.JpaDao;
import com.entry.wepapp.entity.Comment;

/**
 * JPA Implementation of a {@link CommentDao}.
 *
 */
public class JpaCommentDao extends JpaDao<Comment, Long> implements CommentDao
{
    public JpaCommentDao()
    {
        super(Comment.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAll()
    {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Comment> criteriaQuery = builder.createQuery(Comment.class);

        Root<Comment> root = criteriaQuery.from(Comment.class);
        criteriaQuery.orderBy(builder.desc(root.get("date")));

        TypedQuery<Comment> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
    
    @Transactional(readOnly = true)
    public List<Comment> findByPost(Long postId)
    {
    	final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
    	final CriteriaQuery<Comment> criteriaQuery = builder.createQuery(Comment.class);
    	
    	Root<Comment> root = criteriaQuery.from(Comment.class);
    	Path<String> namePath = root.get("postId");
        criteriaQuery.where(builder.equal(namePath, postId));
    	criteriaQuery.orderBy(builder.desc(root.get("date")));
    	
    	TypedQuery<Comment> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
    	return typedQuery.getResultList();
    }
}
