package com.revolut.money.transfer.currency.dao;

import java.math.BigDecimal;

import org.hibernate.SessionFactory;

import com.revolut.money.transfer.model.rates.ExchangeRate;
import com.revolut.money.transfer.model.rates.ExchangeRateKey;

import io.dropwizard.hibernate.AbstractDAO;

public class ExchangeRateDao extends AbstractDAO<ExchangeRate> {

	public ExchangeRateDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public ExchangeRate get(String fromCurrency, String toCurrency) {
		return get(new ExchangeRateKey(fromCurrency, toCurrency));
	}

	public ExchangeRate save(String fromCurrency, String toCurrency, BigDecimal exchangeRate) {
		return persist(new ExchangeRate(new ExchangeRateKey(fromCurrency, toCurrency), exchangeRate));
	}
}
