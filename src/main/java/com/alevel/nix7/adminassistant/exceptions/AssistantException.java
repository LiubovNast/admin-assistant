package com.alevel.nix7.adminassistant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AssistantException {

    public AssistantException() {
    }

    public static ResponseStatusException invalidToken(JwtAuthenticationException cause) {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                "Refresh token is invalid! It may have been rotated, invalidated or expired naturally", cause);
    }
}
