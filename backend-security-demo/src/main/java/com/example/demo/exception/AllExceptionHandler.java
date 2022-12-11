package com.example.demo.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AllExceptionHandler {
	
	@ExceptionHandler(value= {UserNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exc){
		
		ErrorResponse error = new ErrorResponse(exc.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
		
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleAllException(Exception exc){
		ErrorResponse error = new ErrorResponse(exc.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));

		return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}
	
}
