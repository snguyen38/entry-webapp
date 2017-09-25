package com.entry.wepapp.dao.user;

import com.entry.wepapp.dao.Dao;
import com.entry.wepapp.entity.User;

public interface UserDao extends Dao<User, Long>
{
    User loadUserByUsername(String username);

    User findByName(String name);
    
    User findById(Long id);
    
    User findByEmail(String email);
}
