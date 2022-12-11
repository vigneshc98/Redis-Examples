package com.example.demo.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	
	private String message;
	private HttpStatus status;
	private ZonedDateTime time;
	
	public ErrorResponse(String message, HttpStatus status, ZonedDateTime time) {
		super();
		this.message = message;
		this.status = status;
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public ZonedDateTime getTime() {
		return time;
	}

	public void setTime(ZonedDateTime time) {
		this.time = time;
	}
	
	
	

}
