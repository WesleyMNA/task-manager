package com.manager.taskapi.config.handlers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("you cannot do that");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
