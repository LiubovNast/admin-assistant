package com.alevel.nix7.adminassistant.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_OWNER,
    ROLE_ADMIN,
    ROLE_WORKER,
    ROLE_USER,
    ROLE_BAN;

    @Override
    public String getAuthority() {
        return name();
    }
}
