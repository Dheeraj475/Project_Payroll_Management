package com.example.exception;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyLoggedInException.class)
    public ResponseEntity<?> handleUserAlreadyLoggedInException(UserAlreadyLoggedInException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("message", ex.getMessage());
        body.put("token", ex.getToken());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    // Other exception handlers can go here
}