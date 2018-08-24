package com.revolut.money.transfer.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class MoneyOperationResponse {

	@JsonInclude(value = Include.NON_EMPTY)
	private String account;
	private String operation;
	private MoneyDto money;

	@JsonInclude(value = Include.NON_EMPTY)
	private String currentBalance;
	private Status status;

	public MoneyOperationResponse() {
	}

	public MoneyOperationResponse(String account, String operation, MoneyDto money, String currentBalance,
			Status status) {
		this.account = account;
		this.operation = operation;
		this.money = money;
		this.currentBalance = currentBalance;
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

	public MoneyDto getMoney() {
		return money;
	}

	public void setMoney(MoneyDto money) {
		this.money = money;
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

		@JsonInclude(value = Include.NON_EMPTY)
		private String details;

		public static Status ok() {
			return new Status("OK", "");
		}

		public static Status error(String details) {
			return new Status("ERROR", details);
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
