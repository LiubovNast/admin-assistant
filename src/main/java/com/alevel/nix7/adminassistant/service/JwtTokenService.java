package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.exceptions.JwtAuthenticationException;
import com.alevel.nix7.adminassistant.model.token.TokenResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    TokenResponse getToken(UserDetails userDetails);

    void invalidateToken(String token, String login) throws JwtAuthenticationException;
}
