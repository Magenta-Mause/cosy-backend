package com.magentamause.cosybackend.exceptions;

public class NoAuthenticationFoundException extends RuntimeException {
    public NoAuthenticationFoundException() {
        super();
    }

    public NoAuthenticationFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAuthenticationFoundException(Throwable cause) {
        super(cause);
    }
}
