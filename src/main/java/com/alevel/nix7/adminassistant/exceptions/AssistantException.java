package com.alevel.nix7.adminassistant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AssistantException {

    private static final String NOT_FOUND = " not found";

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

    public static ResponseStatusException freeTimeNotFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "FreeTime " + id + NOT_FOUND);
    }

    public static ResponseStatusException recordNotFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Record " + id + NOT_FOUND);
    }

    public static ResponseStatusException adminNotFound(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin " + id + NOT_FOUND);
    }

    public static ResponseStatusException adminNotFound(String login) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin " + login + NOT_FOUND);
    }

    public static ResponseStatusException workerNotFound(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialist " + id + NOT_FOUND);
    }

    public static ResponseStatusException procedureNotFound(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Procedure " + id + NOT_FOUND);
    }

    public static ResponseStatusException userNotFound(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + id + NOT_FOUND);
    }

    public static ResponseStatusException userNotFound(String phone) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with phone " + phone + NOT_FOUND);
    }

    public static ResponseStatusException invalidTime() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input time was wrong");
    }

    public static ResponseStatusException invalidDuration(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }

    public static ResponseStatusException invalidPhone(String phone) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone " + phone + " is invalid");
    }
}
