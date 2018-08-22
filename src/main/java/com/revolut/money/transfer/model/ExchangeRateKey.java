package com.revolut.money.transfer.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ExchangeRateKey implements Serializable {

	private String fromCurrency;
	private String toCurrency;

	public ExchangeRateKey(String fromCurrency, String toCurrency) {
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
	}

	public ExchangeRateKey() {
	}

	public String getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
}
