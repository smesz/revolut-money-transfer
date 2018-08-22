package com.revolut.money.transfer.account.service;

import com.revolut.money.transfer.account.dao.AccountDao;
import com.revolut.money.transfer.account.exception.AccountAlreadyExistsException;
import com.revolut.money.transfer.model.dto.NewAccountRequest;

public class AccountValidator {

	private final AccountDao accountDao;

	public AccountValidator(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	void checkForAccountCreation(NewAccountRequest request) {
		// check if account already exists
		if (accountExists(request.getId())) {
			throw new AccountAlreadyExistsException(request.getId());
		}
	}

	private boolean accountExists(long accountId) {
		return accountDao.exists(accountId);
	}
}
