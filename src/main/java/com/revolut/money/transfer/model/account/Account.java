package com.revolut.money.transfer.model.account;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.revolut.money.transfer.account.exception.NotEnoughMoneyException;

@Entity
@Table(name = "accounts")
public class Account {

	@Id
	private long id;

	private String currency;
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private List<AccountOperation> accountOperations;

	public Account() {
	}

	public Account(long id, String name, String currency) {
		this.id = id;
		this.name = name;
		this.currency = currency;
		this.accountOperations = new ArrayList<>();
	}

	@Transient
	public void makeDeposit(BigDecimal amount, BigDecimal amountInAccountCurrency, String currency) {
		getAccountOperations().add(
				new DepositOperation(amount, amountInAccountCurrency, currency, new Date())
		);
	}

	@Transient
	public void makeWithdraw(BigDecimal amount, BigDecimal amountInAccountCurrency, String currency) {

		// check if there is enough money on account
		if (getBalance().compareTo(amountInAccountCurrency) < 0) {
			throw new NotEnoughMoneyException(this.getId());
		}

		getAccountOperations().add(
				new WithdrawOperation(amount, amountInAccountCurrency, currency, new Date())
		);
	}

	@Transient
	public BigDecimal getBalance() {
		BigDecimal balance = BigDecimal.ZERO;
		for (AccountOperation operation : getAccountOperations()) {
			balance = operation.apply(balance);
		}

		return balance.setScale(2, RoundingMode.DOWN);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AccountOperation> getAccountOperations() {
		return accountOperations;
	}

	public void setAccountOperations(List<AccountOperation> accountOperations) {
		this.accountOperations = accountOperations;
	}
}
