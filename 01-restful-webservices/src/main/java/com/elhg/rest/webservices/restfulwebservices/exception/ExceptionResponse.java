package com.elhg.rest.webservices.restfulwebservices.exception;

import java.util.Date;

public class ExceptionResponse {
	
	private java.util.Date timestamp;
	private String message;
	private String details;
	
	public java.util.Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public ExceptionResponse(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	
	
}
