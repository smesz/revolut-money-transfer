package com.revolut.money.transfer.account.service;

import java.math.BigDecimal;

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
	private final DepositFactory depositFactory;
	private final WithdrawFactory withdrawFactory;

	public AccountService(AccountDao accountDao, AccountConverter converter, DepositFactory depositFactory,
			WithdrawFactory withdrawFactory) {
		this.accountDao = accountDao;
		this.converter = converter;
		this.depositFactory = depositFactory;
		this.withdrawFactory = withdrawFactory;
	}

	public NewAccountResponse createAccount(NewAccountRequest request) {
		Account account = accountDao.create(createAccountObject(request));
		return converter.toResponse(account);
	}

	public MoneyOperationResponse makeDeposit(long accountId, MoneyOperationRequest request) {

		Account account = accountDao.getOrThrowException(accountId);
		BigDecimal amount = MoneyFormatter.parse(request.getAmount(), 2);

		account.makeDeposit(
				depositFactory.create(amount, request.getCurrency(), account.getCurrency())
		);

		return okResponse(account, "deposit", request);
	}

	private MoneyOperationResponse okResponse(Account account, String operation, MoneyOperationRequest request) {
		return new MoneyOperationResponse(
				account.getName(),
				MoneyFormatter.format(account.getBalance()),
				Status.ok(operation, new MoneyDto(request.getAmount(), request.getCurrency()))
		);
	}

	public MoneyOperationResponse makeWithdraw(long accountId, MoneyOperationRequest request) {
		Account account = accountDao.getOrThrowException(accountId);
		BigDecimal amount = MoneyFormatter.parse(request.getAmount(), 2);

		account.makeWithdraw(
				withdrawFactory.create(amount, request.getCurrency(), account.getCurrency())
		);

		return okResponse(account, "withdraw", request);
	}

	private Account createAccountObject(NewAccountRequest request) {
		return converter.fromRequest(request);
	}

	public BalanceResponse getBalance(long accountId) {
		Account account = accountDao.getOrThrowException(accountId);

		return new BalanceResponse(account.getName(), MoneyFormatter.format(account.getBalance()),
				account.getCurrency());
	}
}
