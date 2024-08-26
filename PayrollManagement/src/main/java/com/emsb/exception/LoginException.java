package com.emsb.exception;

public class LoginException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private final String token;

    public LoginException(String message, String token) {
        super(message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}