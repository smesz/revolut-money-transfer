package com.revolut.money.transfer.account.exception;

public class AccountNotExistsException extends RuntimeException {

	private static final String ERROR_TEMPLATE = "Account with id '%s' does not exists";

	public AccountNotExistsException(long id) {
		super(String.format(ERROR_TEMPLATE, id));
	}
}
