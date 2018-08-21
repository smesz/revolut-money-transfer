package com.revolut.money.transfer.person.exception;

public class UserDoesNotExistException extends RuntimeException {

	private static final String ERROR_TEMPLATE = "User with id '%s' does not exist";

	public UserDoesNotExistException(long id) {
		super(String.format(ERROR_TEMPLATE, id));
	}
}
