package com.revolut.money.transfer.model.account;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("withdraw")
public class WithdrawOperation extends AccountOperation {

	public WithdrawOperation(BigDecimal amount, BigDecimal amountInAccountCurrency, String currency, Date createdOn) {
		super(amount, amountInAccountCurrency, currency, createdOn);
	}

	public WithdrawOperation() {
	}

	@Override
	BigDecimal apply(BigDecimal actualBalance) {
		return actualBalance.subtract(getAmountInAccountCurrency());
	}
}
