package com.revolut.money.transfer.currency;

import java.math.BigDecimal;

public interface ExchangeService {

	BigDecimal getExchangeRate(String fromCurrency, String toCurrency);
}
