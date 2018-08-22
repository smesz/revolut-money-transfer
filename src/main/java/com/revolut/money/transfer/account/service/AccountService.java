package com.revolut.money.transfer.account.service;

import com.revolut.money.transfer.account.dao.AccountDao;
import com.revolut.money.transfer.model.account.Account;
import com.revolut.money.transfer.model.dto.BalanceResponse;
import com.revolut.money.transfer.model.dto.MoneyOperationRequest;
import com.revolut.money.transfer.model.dto.MoneyOperationResponse;
import com.revolut.money.transfer.model.dto.MoneyOperationResponse.MoneyDto;
import com.revolut.money.transfer.model.dto.MoneyOperationResponse.Status;
import com.revolut.money.transfer.model.dto.NewAccountRequest;
import com.revolut.money.transfer.model.dto.NewAccountResponse;
import com.revolut.money.transfer.util.MoneyFormatter;

public class AccountService {

	private final AccountDao accountDao;
	private final AccountConverter converter;
	private final AccountValidator validator;
	private final MoneyOperationsExecutor moneyOperationsExecutor;

	public AccountService(AccountDao accountDao, AccountConverter converter, AccountValidator validator,
			MoneyOperationsExecutor moneyOperationsExecutor) {

		this.accountDao = accountDao;
		this.converter = converter;
		this.validator = validator;
		this.moneyOperationsExecutor = moneyOperationsExecutor;
	}

	public NewAccountResponse createAccount(NewAccountRequest request) {
		validator.checkForAccountCreation(request);

		Account account = accountDao.create(createAccountObject(request));
		return converter.toResponse(account);
	}

	public MoneyOperationResponse makeDeposit(long accountId, MoneyOperationRequest request) {
		Account account = accountDao.findById(accountId);

		moneyOperationsExecutor.forAccount(account)
				.deposit(MoneyFormatter.parse(request.getAmount()))
				.inCurrency(request.getCurrency())
				.execute();

		return createOperationResponse(account, "deposit", request);
	}

	private MoneyOperationResponse createOperationResponse(Account account, String operation,
			MoneyOperationRequest request) {

		return new MoneyOperationResponse(
				account.getName(), operation, new MoneyDto(request.getAmount(), request.getCurrency()),
				MoneyFormatter.format(account.getBalance()), Status.ok()
		);
	}

	public MoneyOperationResponse makeWithdraw(long accountId, MoneyOperationRequest request) {
		Account account = accountDao.findById(accountId);

		moneyOperationsExecutor.forAccount(account)
				.withdraw(MoneyFormatter.parse(request.getAmount()))
				.inCurrency(request.getCurrency())
				.execute();

		return createOperationResponse(account, "withdraw", request);
	}

	private Account createAccountObject(NewAccountRequest request) {
		return converter.fromRequest(request);
	}

	public BalanceResponse getBalance(long accountId) {
		Account account = accountDao.findById(accountId);

		return new BalanceResponse(account.getName(), String.valueOf(account.getBalance()), account.getCurrency());
	}
}
