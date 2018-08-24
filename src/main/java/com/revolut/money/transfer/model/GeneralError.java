package com.revolut.money.transfer.model;

import java.io.Serializable;

public class GeneralError implements Serializable {

	private String error;

	public GeneralError(String error) {
		this.error = error;
	}

	public GeneralError() {
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
