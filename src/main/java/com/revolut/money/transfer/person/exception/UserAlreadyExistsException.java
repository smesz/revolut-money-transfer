package com.revolut.money.transfer.person.exception;

public class UserAlreadyExistsException extends RuntimeException {

	private static final String ERROR_TEMPLATE = "Person with id '%s' already exists";

	public UserAlreadyExistsException(long id) {
		super(String.format(ERROR_TEMPLATE, id));
	}
}
