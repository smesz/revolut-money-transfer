package com.revolut.money.transfer.account.service;

import java.util.Date;

import com.revolut.money.transfer.account.dao.AccountDao;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.DepositOperation;
import com.revolut.money.transfer.model.Person;
import com.revolut.money.transfer.model.WithdrawOperation;
import com.revolut.money.transfer.model.account.BalanceResponse;
import com.revolut.money.transfer.model.account.MoneyOperationRequest;
import com.revolut.money.transfer.model.account.NewAccountRequest;
import com.revolut.money.transfer.model.account.NewAccountResponse;
import com.revolut.money.transfer.person.service.UserService;

public class AccountService {

	private final AccountDao accountDao;
	private final UserService userService;
	private final AccountConverter converter;
	private final AccountValidator validator;

	public AccountService(AccountDao accountDao, UserService userService, AccountConverter converter,
			AccountValidator validator) {
		this.accountDao = accountDao;
		this.userService = userService;
		this.converter = converter;
		this.validator = validator;
	}

	public NewAccountResponse createAccount(NewAccountRequest request) {
		validator.checkForAccountCreation(request);

		Account account = accountDao.create(createAccountObject(request));
		return converter.toResponse(account);
	}

	public void makeDeposit(long accountId, MoneyOperationRequest request) {
		Account account = accountDao.findById(accountId);

		account.addOperation(new DepositOperation(request.getAmount(), request.getCurrency(), new Date()));
	}

	public void makeWithdraw(long accountId, MoneyOperationRequest request) {
		Account account = accountDao.findById(accountId);

		account.addOperation(new WithdrawOperation(request.getAmount(), request.getCurrency(), new Date()));
	}

	private Account createAccountObject(NewAccountRequest request) {
		Person accountOwner = getOwner(request.getOwnerId());
		return converter.fromRequest(request, accountOwner);
	}

	private Person getOwner(long personId) {
		return userService.getUser(personId);
	}

	public BalanceResponse getBalance(long accountId) {
		Account account = accountDao.findById(accountId);

		return new BalanceResponse(account.getName(), String.valueOf(account.getBalance()), account.getCurrency());
	}
}
