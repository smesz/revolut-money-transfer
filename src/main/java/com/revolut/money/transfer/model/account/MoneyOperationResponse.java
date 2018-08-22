package com.revolut.money.transfer.model.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.revolut.money.transfer.model.MoneyDto;

public class MoneyOperationResponse {

	private String account;
	private String operation;
	private MoneyDto amount;
	private Status status;

	public MoneyOperationResponse() {
	}

	public MoneyOperationResponse(String account, String operation, MoneyDto amount,
			Status status) {
		this.account = account;
		this.operation = operation;
		this.amount = amount;
		this.status = status;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public MoneyDto getAmount() {
		return amount;
	}

	public void setAmount(MoneyDto amount) {
		this.amount = amount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static class Status {

		private String code;

		@JsonInclude(value = Include.NON_EMPTY)
		private String details;

		public static Status ok() {
			return new Status("OK", "");
		}

		public Status() {
		}

		public Status(String code, String details) {
			this.code = code;
			this.details = details;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}
	}
}
