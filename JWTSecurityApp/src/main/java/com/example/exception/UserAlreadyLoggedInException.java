package com.example.exception;


public class UserAlreadyLoggedInException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String token;

    public UserAlreadyLoggedInException(String message, String token) {
        super(message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}