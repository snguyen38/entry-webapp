package com.entry.webapp.dao;

import java.util.List;

import com.entry.webapp.entity.Entity;

public interface Dao<T extends Entity, I>
{
    List<T> findAll();

    T find(I id);

    T save(T entity);

    void delete(I id);

    void delete(T entity);
}
