package com.groups.groupsmanager.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GroupAlreadyExistsException extends RuntimeException {
	public GroupAlreadyExistsException(String message) {
		super(message);
	}

}
