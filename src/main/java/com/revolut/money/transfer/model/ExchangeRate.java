package com.revolut.money.transfer.model;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ExchangeRate {

	@EmbeddedId
	private ExchangeRateKey id;

	private BigDecimal exchangeRate;

	public ExchangeRateKey getId() {
		return id;
	}

	public void setId(ExchangeRateKey id) {
		this.id = id;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
}
