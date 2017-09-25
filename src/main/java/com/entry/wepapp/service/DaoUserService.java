package com.entry.wepapp.service;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.entry.wepapp.dao.accesstoken.AccessTokenDao;
import com.entry.wepapp.dao.user.UserDao;
import com.entry.wepapp.entity.AccessToken;
import com.entry.wepapp.entity.User;

public class DaoUserService implements UserService
{
    private UserDao userDao;

    private AccessTokenDao accessTokenDao;

    protected DaoUserService()
    {
        /* Reflection instantiation */
    }

    public DaoUserService(UserDao userDao, AccessTokenDao accessTokenDao)
    {
        this.userDao = userDao;
        this.accessTokenDao = accessTokenDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return this.userDao.loadUserByUsername(username);
    }

    @Override
    @Transactional
    public User findUserByAccessToken(String accessTokenString)
    {
        AccessToken accessToken = this.accessTokenDao.findByToken(accessTokenString);

        if (null == accessToken) {
            return null;
        }

        if (accessToken.isExpired()) {
            this.accessTokenDao.delete(accessToken);
            return null;
        }

        return accessToken.getUser();
    }

    @Override
    @Transactional
    public AccessToken createAccessToken(User user)
    {
        AccessToken accessToken = new AccessToken(user, UUID.randomUUID().toString());
        return this.accessTokenDao.save(accessToken);
    }

	@Override
	@Transactional
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userDao.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserByNickName(String nickName) throws UsernameNotFoundException {
		return this.userDao.loadUserByUsername(nickName);
	}

	@Override
	public User findUserById(Long id) {
		return this.userDao.findById(id);
	}

	@Override
	public User findUserByEmail(String email) {
		return this.userDao.findByEmail(email);
	}
}
