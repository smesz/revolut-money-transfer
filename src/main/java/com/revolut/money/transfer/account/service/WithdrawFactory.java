package com.revolut.money.transfer.account.service;

import java.math.BigDecimal;
import java.util.Date;

import com.revolut.money.transfer.account.service.operations.AbstractAccountOperationFactory;
import com.revolut.money.transfer.currency.ExchangeRateService;
import com.revolut.money.transfer.model.account.AccountOperation;
import com.revolut.money.transfer.model.account.WithdrawOperation;

public class WithdrawFactory extends AbstractAccountOperationFactory {

	public WithdrawFactory(ExchangeRateService exchangeRateService) {
		super(exchangeRateService);
	}

	@Override
	protected AccountOperation createOperation(BigDecimal amount, BigDecimal amountInAccountCurrency, String currency) {
		return new WithdrawOperation(amount, amountInAccountCurrency, currency, new Date());
	}
}
