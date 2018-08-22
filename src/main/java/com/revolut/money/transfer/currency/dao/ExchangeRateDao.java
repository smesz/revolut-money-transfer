package com.revolut.money.transfer.currency.dao;

import org.hibernate.SessionFactory;

import com.revolut.money.transfer.model.ExchangeRate;
import com.revolut.money.transfer.model.ExchangeRateKey;

import io.dropwizard.hibernate.AbstractDAO;

public class ExchangeRateDao extends AbstractDAO<ExchangeRate> {

	public ExchangeRateDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public ExchangeRate get(String fromCurrency, String toCurrency) {
		return get(new ExchangeRateKey(fromCurrency, toCurrency));
	}
}
