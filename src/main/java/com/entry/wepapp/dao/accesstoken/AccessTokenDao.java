package com.entry.wepapp.dao.accesstoken;

import com.entry.wepapp.dao.Dao;
import com.entry.wepapp.entity.AccessToken;

public interface AccessTokenDao extends Dao<AccessToken, Long>
{
    AccessToken findByToken(String accessTokenString);
}
