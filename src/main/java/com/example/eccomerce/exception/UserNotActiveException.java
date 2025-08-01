package com.example.eccomerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException(String username) {
        super("User account with username " + username + " is not active");
    }
}

