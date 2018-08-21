package com.revolut.money.transfer.model;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "OPERATION_TYPE")
public abstract class AccountOperation {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	protected long id;

	protected Date createdOn;
	protected double amount;
	protected String currency;

	public AccountOperation() {
	}

	public AccountOperation(double amount, String currency, Date createdOn) {
		this.amount = amount;
		this.currency = currency;
		this.createdOn = createdOn;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	abstract double apply(double actualBalance);
}
