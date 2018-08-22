package com.revolut.money.transfer.account.exception;

public class NotEnoughMoneyException extends RuntimeException {

	private static final String ERROR_TEMPLATE = "Not enough money on account '%s'";

	public NotEnoughMoneyException(long accountId) {
		super(String.format(ERROR_TEMPLATE, accountId));
	}
}
