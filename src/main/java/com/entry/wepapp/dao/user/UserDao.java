package com.entry.wepapp.dao.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.entry.wepapp.dao.Dao;
import com.entry.wepapp.entity.User;

public interface UserDao extends Dao<User, Long>
{
    User loadUserByUsername(String username) throws UsernameNotFoundException;

    User findByName(String name);
    
    User findById(Long id);
}
