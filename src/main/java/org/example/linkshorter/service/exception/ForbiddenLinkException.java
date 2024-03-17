package org.example.linkshorter.service.exception;

public class ForbiddenLinkException extends RuntimeException {
    public ForbiddenLinkException(String message) {
        super(message);
    }
}
