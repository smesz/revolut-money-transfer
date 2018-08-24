package com.revolut.money.transfer.model.dto;

public class NewAccountRequest {

	private String name;
	private String currency;

	public NewAccountRequest(String name, String currency) {
		this.name = name;
		this.currency = currency;
	}

	public NewAccountRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
