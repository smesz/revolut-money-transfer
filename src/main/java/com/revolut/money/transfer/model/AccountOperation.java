package com.revolut.money.transfer.model;

import java.math.BigDecimal;
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
	protected BigDecimal amount;
	protected BigDecimal amountInAccountCurrency;
	protected String currency;

	public AccountOperation() {
	}

	public AccountOperation(BigDecimal amount, BigDecimal amountInAccountCurrency, String currency, Date createdOn) {
		this.amount = amount;
		this.amountInAccountCurrency = amountInAccountCurrency;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountInAccountCurrency() {
		return amountInAccountCurrency;
	}

	public void setAmountInAccountCurrency(BigDecimal amountInAccountCurrency) {
		this.amountInAccountCurrency = amountInAccountCurrency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	abstract BigDecimal apply(BigDecimal actualBalance);
}
