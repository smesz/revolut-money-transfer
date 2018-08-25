package com.revolut.money.transfer.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class MoneyOperationResponse {

	@JsonInclude(value = Include.NON_EMPTY)
	private String account;

	@JsonInclude(value = Include.NON_EMPTY)
	private String currentBalance;
	private Status status;

	public MoneyOperationResponse() {
	}

	public MoneyOperationResponse(String account, String currentBalance, Status status) {
		this.account = account;
		this.currentBalance = currentBalance;
		this.status = status;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static class Status {

		private String code;
		private String operation;
		private MoneyDto transfer;

		@JsonInclude(value = Include.NON_EMPTY)
		private String details;

		public static Status ok(String operation, MoneyDto transfer) {
			return new Status(operation, transfer, "OK", "");
		}

		public static Status error(String operation, MoneyDto transfer, String details) {
			return new Status(operation, transfer, "ERROR", details);
		}

		public Status() {
		}

		public Status(String operation, MoneyDto transfer, String code, String details) {
			this.operation = operation;
			this.transfer = transfer;
			this.code = code;
			this.details = details;
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public MoneyDto getTransfer() {
			return transfer;
		}

		public void setTransfer(MoneyDto transfer) {
			this.transfer = transfer;
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

	public static class MoneyDto {

		private String amount;
		private String currency;

		public MoneyDto() {
		}

		public MoneyDto(String amount, String currency) {
			this.amount = amount;
			this.currency = currency;
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
}
