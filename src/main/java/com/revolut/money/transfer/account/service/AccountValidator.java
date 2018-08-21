package com.revolut.money.transfer.account.service;

import com.revolut.money.transfer.account.dao.AccountDao;
import com.revolut.money.transfer.account.exception.AccountAlreadyExistsException;
import com.revolut.money.transfer.model.account.NewAccountRequest;
import com.revolut.money.transfer.person.dao.UserDao;
import com.revolut.money.transfer.person.exception.UserDoesNotExistException;

public class AccountValidator {

	private final AccountDao accountDao;
	private final UserDao userDao;

	public AccountValidator(AccountDao accountDao, UserDao userDao) {
		this.accountDao = accountDao;
		this.userDao = userDao;
	}

	void checkForAccountCreation(NewAccountRequest request) {

		// check if account already exists
		if (accountExists(request.getId())) {
			throw new AccountAlreadyExistsException(request.getId());
		}

		// check if user exists in the system
		if (!userExists(request.getOwnerId())) {
			throw new UserDoesNotExistException(request.getOwnerId());
		}
	}

	private boolean accountExists(long accountId) {
		return accountDao.exists(accountId);
	}

	private boolean userExists(long userId) {
		return userDao.exists(userId);
	}

}
