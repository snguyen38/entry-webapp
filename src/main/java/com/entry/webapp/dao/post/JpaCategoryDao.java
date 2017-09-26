package com.entry.webapp.dao.post;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Transactional;

import com.entry.webapp.dao.JpaDao;
import com.entry.webapp.entity.Category;

/**
 * JPA Implementation of a {@link CategoryDao}.
 *
 */
public class JpaCategoryDao extends JpaDao<Category, Long> implements CategoryDao
{
    public JpaCategoryDao()
    {
        super(Category.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll()
    {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Category> criteriaQuery = builder.createQuery(Category.class);

        criteriaQuery.from(Category.class);

        TypedQuery<Category> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
