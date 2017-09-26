package com.entry.webapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.entry.webapp.entity.AccessToken;
import com.entry.webapp.entity.User;

public interface UserService extends UserDetailsService
{
    User findUserByAccessToken(String accessToken);

    AccessToken createAccessToken(User user);
    
    User saveUser(User user);
    
    User findUserByNickName(String nickName);
    
    User findUserById(Long id);
    
    User findUserByEmail(String email);
}
