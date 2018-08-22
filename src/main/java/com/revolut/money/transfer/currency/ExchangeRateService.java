package com.revolut.money.transfer.currency;

import java.math.BigDecimal;

public interface ExchangeRateService {

	BigDecimal getExchangeRate(String fromCurrency, String toCurrency);
}
