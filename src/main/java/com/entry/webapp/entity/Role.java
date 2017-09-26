package com.entry.webapp.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority
{
    ADMIN("ROLE_ADMIN");

    private String authority;

    Role(String authority)
    {
        this.authority = authority;
    }

    @Override
    public String getAuthority()
    {
        return this.authority;
    }
}
