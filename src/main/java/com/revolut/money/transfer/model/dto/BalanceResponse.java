package com.revolut.money.transfer.model.dto;

public class BalanceResponse {

	private String account;
	private String balance;
	private String currency;

	public BalanceResponse(String account, String balance, String currency) {
		this.account = account;
		this.balance = balance;
		this.currency = currency;
	}

	public BalanceResponse() {
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
