package com.emsb.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;



@ControllerAdvice
public class EmployeesGlobalExceptionHandler {
	
	public ResponseEntity<ErrorResponse> handleEmployeesException(EmployeesException exception){
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(LoginException.class)
    public ResponseEntity<?> handleLoggedInException(LoginException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", new Date().toString());
        body.put("message", ex.getMessage());
        body.put("token", ex.getToken());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    // Other exception handlers can go here
}


