package com.entry.wepapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.entry.wepapp.entity.AccessToken;
import com.entry.wepapp.entity.User;

public interface UserService extends UserDetailsService
{
    User findUserByAccessToken(String accessToken);

    AccessToken createAccessToken(User user);
    
    User saveUser(User user);
    
    User findUserByNickName(String nickName);
    
    User findUserById(Long id);
}
