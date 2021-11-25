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

    public static ResponseStatusException duplicateLogin(String login) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login " + login + " already taken");
    }

    public static ResponseStatusException duplicatePhone(String phone) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with phone " + phone + " already exists");
    }

    public static ResponseStatusException freeTimeNotFound(String name) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialist " + name + " doesn't have free time for this");
    }

    public static ResponseStatusException adminNotFound(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin " + id + " not found");
    }

    public static ResponseStatusException workerNotFound(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialist " + id + " not found");
    }

    public static ResponseStatusException userNotFound(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + id + " not found");
    }
}
