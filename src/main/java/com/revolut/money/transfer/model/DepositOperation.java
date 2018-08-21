package com.revolut.money.transfer.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("deposit")
public class DepositOperation extends AccountOperation {

	public DepositOperation(double amount, String currency, Date createdOn) {
		super(amount, currency, createdOn);
	}

	public DepositOperation() {
	}

	@Override
	double apply(double actualBalance) {
		return actualBalance + getAmount();
	}
}
