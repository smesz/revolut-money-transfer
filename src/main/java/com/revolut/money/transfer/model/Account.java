package com.revolut.money.transfer.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "accounts")
public class Account {

	@Id
	private long id;

	private String currency;
	private String name;

	@ManyToOne
	@JoinColumn(name = "fk_owner")
	private Person owner;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private List<AccountOperation> accountOperations;

	public Account() {
	}

	public Account(long id, String name, String currency, Person accountOwner) {
		this.id = id;
		this.name = name;
		this.currency = currency;
		this.owner = accountOwner;
		this.accountOperations = new ArrayList<>();
	}

	@Transient
	public void addOperation(AccountOperation operation) {
		getAccountOperations().add(operation);
	}

	@Transient
	public double getBalance() {
		double balance = 0.0;
		for (AccountOperation operation : getAccountOperations()) {
			balance = operation.apply(balance);
		}

		return balance;
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

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public List<AccountOperation> getAccountOperations() {
		return accountOperations;
	}

	public void setAccountOperations(List<AccountOperation> accountOperations) {
		this.accountOperations = accountOperations;
	}
}
