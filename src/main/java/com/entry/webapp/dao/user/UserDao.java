package com.entry.webapp.dao.user;

import com.entry.webapp.dao.Dao;
import com.entry.webapp.entity.User;

public interface UserDao extends Dao<User, Long>
{
    User loadUserByUsername(String username);

    User findByName(String name);
    
    User findById(Long id);
    
    User findByEmail(String email);
}
