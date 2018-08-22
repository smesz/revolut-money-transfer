package com.revolut.money.transfer.account.service;

import java.math.BigDecimal;

import com.revolut.money.transfer.currency.ExchangeService;
import com.revolut.money.transfer.currency.MoneyExchangeService;
import com.revolut.money.transfer.model.account.Account;

public class MoneyOperationsExecutor {

	private final ExchangeService exchangeRateService;

	public MoneyOperationsExecutor(MoneyExchangeService exchangeRateService) {
		this.exchangeRateService = exchangeRateService;
	}

	public Builder forAccount(Account account) {
		return new Builder(account, exchangeRateService);
	}

	public static class Builder {

		private ExchangeService moneyExchangeService;
		private Account account;
		private String operation;
		private BigDecimal amount;
		private String currency;

		Builder(Account account, ExchangeService moneyExchangeService) {
			this.account = account;
			this.moneyExchangeService = moneyExchangeService;
		}

		Builder deposit(BigDecimal amount) {
			this.operation = "deposit";
			this.amount = amount;
			return this;
		}

		Builder withdraw(BigDecimal amount) {
			this.operation = "withdraw";
			this.amount = amount;
			return this;
		}

		Builder inCurrency(String currency) {
			this.currency = currency;
			return this;
		}

		void execute() {
			BigDecimal amountInAccountCurrency = amount.multiply(getExchangeRate());

			if ("deposit".equals(operation)) {
				account.makeDeposit(amount, amountInAccountCurrency, currency);
			} else if ("withdraw".equals(operation)) {
				account.makeWithdraw(amount, amountInAccountCurrency, currency);
			}
		}

		private BigDecimal getExchangeRate() {
			if (!account.getCurrency().equals(currency)) {
				return moneyExchangeService.getExchangeRate(currency, account.getCurrency());
			}

			return BigDecimal.ONE;
		}
	}
}