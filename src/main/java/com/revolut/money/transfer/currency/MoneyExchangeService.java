package com.revolut.money.transfer.currency;

import java.math.BigDecimal;

import com.revolut.money.transfer.currency.dao.ExchangeRateDao;
import com.revolut.money.transfer.model.rates.ExchangeRate;

public class MoneyExchangeService implements ExchangeService {

	private final ExchangeRateDao dao;

	public MoneyExchangeService(ExchangeRateDao dao) {
		this.dao = dao;
	}

	@Override
	public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
		ExchangeRate exchangeRate = dao.get(fromCurrency, toCurrency);
		return exchangeRate.getExchangeRate();
	}
}
