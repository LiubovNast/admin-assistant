package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.exceptions.JwtAuthenticationException;
import com.alevel.nix7.adminassistant.model.details.AdminDetails;
import com.alevel.nix7.adminassistant.model.token.TokenResponse;

public interface JwtTokenService {

    TokenResponse getToken(AdminDetails userDetails);

    void invalidateToken(String token, String login) throws JwtAuthenticationException;
}
