package com.revolut.money.transfer.account.service;

import java.math.BigDecimal;

import com.revolut.money.transfer.currency.ExchangeRateService;
import com.revolut.money.transfer.currency.MoneyExchangeRateService;
import com.revolut.money.transfer.model.account.Account;

public class MoneyOperationsExecutor {

	private final ExchangeRateService exchangeRateService;

	public MoneyOperationsExecutor(MoneyExchangeRateService exchangeRateService) {
		this.exchangeRateService = exchangeRateService;
	}

	public Builder forAccount(Account account) {
		return new Builder(account, exchangeRateService);
	}

	public static class Builder {

		private ExchangeRateService moneyExchangeRateService;
		private Account account;
		private String operation;
		private BigDecimal amount;
		private String currency;

		Builder(Account account, ExchangeRateService moneyExchangeRateService) {
			this.account = account;
			this.moneyExchangeRateService = moneyExchangeRateService;
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
				return moneyExchangeRateService.getExchangeRate(currency, account.getCurrency());
			}

			return BigDecimal.ONE;
		}
	}
}