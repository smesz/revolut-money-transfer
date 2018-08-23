package com.revolut.money.transfer.model.dto;

public class NewAccountRequest {

	private long id;
	private String name;
	private String currency;

	public NewAccountRequest(long id, String name, String currency) {
		this.id = id;
		this.name = name;
		this.currency = currency;
	}

	public NewAccountRequest() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
