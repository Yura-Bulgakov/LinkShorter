package org.example.linkshorter.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class ForbiddenLinkException extends RuntimeException {
    public ForbiddenLinkException(String message) {
        super(message);
    }
}
