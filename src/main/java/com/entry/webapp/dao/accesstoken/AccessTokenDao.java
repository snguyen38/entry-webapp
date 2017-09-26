package com.entry.webapp.dao.accesstoken;

import com.entry.webapp.dao.Dao;
import com.entry.webapp.entity.AccessToken;

public interface AccessTokenDao extends Dao<AccessToken, Long>
{
    AccessToken findByToken(String accessTokenString);
}
