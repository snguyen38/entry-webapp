package com.entry.webapp.dao.post;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Transactional;

import com.entry.webapp.dao.JpaDao;
import com.entry.webapp.entity.Country;

/**
 * JPA Implementation of a {@link CountryDao}.
 *
 */
public class JpaCountryDao extends JpaDao<Country, Long> implements CountryDao
{
    public JpaCountryDao()
    {
        super(Country.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> findAll()
    {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Country> criteriaQuery = builder.createQuery(Country.class);

        criteriaQuery.from(Country.class);

        TypedQuery<Country> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
