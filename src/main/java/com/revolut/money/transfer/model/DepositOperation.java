package com.revolut.money.transfer.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("deposit")
public class DepositOperation extends AccountOperation {

	public DepositOperation(BigDecimal amount, BigDecimal amountInAccountCurrency, String currency, Date createdOn) {
		super(amount, amountInAccountCurrency, currency, createdOn);
	}

	public DepositOperation() {
	}

	@Override
	BigDecimal apply(BigDecimal actualBalance) {
		return actualBalance.add(getAmountInAccountCurrency());
	}
}
