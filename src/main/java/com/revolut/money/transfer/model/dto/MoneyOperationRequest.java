package com.revolut.money.transfer.model.dto;

public class MoneyOperationRequest {

	private String amount;
	private String currency;

	public MoneyOperationRequest(String amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public MoneyOperationRequest() {
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
