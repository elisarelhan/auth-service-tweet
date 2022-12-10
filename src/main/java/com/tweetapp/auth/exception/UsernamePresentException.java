package com.tweetapp.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UsernamePresentException extends RuntimeException {
	public UsernamePresentException() {
		super();
	}

	public UsernamePresentException(String message) {
		super(message);
	}

	public UsernamePresentException(String message, Throwable cause) {
		super(message, cause);
	}
}
