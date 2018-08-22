package com.revolut.money.transfer.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyFormatter {

	public static String format(BigDecimal amount) {
		return amount.setScale(6, RoundingMode.DOWN).stripTrailingZeros().toString();
	}

	public static BigDecimal parse(String amount) {
		return new BigDecimal(amount).setScale(6, RoundingMode.DOWN);
	}
}
