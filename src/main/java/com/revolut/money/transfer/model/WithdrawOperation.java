package com.revolut.money.transfer.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("withdraw")
public class WithdrawOperation extends AccountOperation {

	public WithdrawOperation(double amount, String currency, Date createdOn) {
		super(amount, currency, createdOn);
	}

	@Override
	double apply(double actualBalance) {
		return actualBalance - getAmount();
	}
}
