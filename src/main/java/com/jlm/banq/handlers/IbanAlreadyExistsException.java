package com.jlm.banq.handlers;

public class IbanAlreadyExistsException extends RuntimeException {
    public IbanAlreadyExistsException(String message) {
        super(message);
    }
}
