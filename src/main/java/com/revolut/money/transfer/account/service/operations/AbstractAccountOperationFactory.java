package com.revolut.money.transfer.account.service.operations;

import java.math.BigDecimal;

import com.revolut.money.transfer.currency.ExchangeRateService;
import com.revolut.money.transfer.model.account.AccountOperation;

public abstract class AbstractAccountOperationFactory {

	private final ExchangeRateService exchangeRateService;

	public AbstractAccountOperationFactory(ExchangeRateService exchangeRateService) {
		this.exchangeRateService = exchangeRateService;
	}

	public AccountOperation create(BigDecimal amount, String currency, String accountCurrency) {
		BigDecimal amountInAccountCurrency =
				exchangeRateService.convertMoneyByExchangeRate(amount, currency, accountCurrency);

		return createOperation(amount, amountInAccountCurrency, currency);
	}

	protected abstract AccountOperation createOperation(BigDecimal amount, BigDecimal amountInAccountCurrency,
			String currency);
}
