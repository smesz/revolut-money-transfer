package com.revolut.money.transfer.account.exception;

public class AccountAlreadyExistsException extends RuntimeException {

	private static final String ERROR_TEMPLATE = "Account with id '%s' already exists";

	public AccountAlreadyExistsException(long id) {
		super(String.format(ERROR_TEMPLATE, id));
	}
}