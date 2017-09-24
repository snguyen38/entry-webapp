package com.entry.wepapp.dao.blogpost;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Path;

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
        criteriaQuery.orderBy(builder.desc(root.get("id")));

        TypedQuery<Post> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
    
    @Override
    @Transactional()
    public boolean updateLikeAndDislikeNumber(long postId, long likeNumber, long dislikeNumber)
    {
    	final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
    	final CriteriaUpdate<Post> criteriaUpdate = builder.createCriteriaUpdate(Post.class);
    	
    	Root<Post> root = criteriaUpdate.from(Post.class);
    	criteriaUpdate.set(root.get("likeNumber"), likeNumber);
    	criteriaUpdate.set(root.get("dislikeNumber"), dislikeNumber);
    	criteriaUpdate.where(builder.equal(root.get("id"), postId));
    	
    	Query typedQuery = this.getEntityManager().createQuery(criteriaUpdate);
    	return typedQuery.executeUpdate() > 0;
    }

	@Override
	public List<Post> findPostByUser(String username) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);

        Root<Post> root = criteriaQuery.from(Post.class);
        Path<String> namePath = root.get("username");
        criteriaQuery.where(builder.equal(namePath, username));
        criteriaQuery.orderBy(builder.desc(root.get("id")));

        TypedQuery<Post> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
	}
}
