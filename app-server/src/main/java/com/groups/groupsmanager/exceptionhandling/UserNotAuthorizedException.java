package com.groups.groupsmanager.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotAuthorizedException extends Exception {
	public UserNotAuthorizedException(String message) {
		super(message);
	}

}
