package com.revolut.money.transfer.account.service;

import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.dto.NewAccountRequest;
import com.revolut.money.transfer.model.dto.NewAccountResponse;

public class AccountConverter {

	public Account fromRequest(NewAccountRequest request) {
		return new Account(
				request.getId(),
				request.getName(),
				request.getCurrency()
		);
	}

	public NewAccountResponse toResponse(Account account) {
		NewAccountResponse response = new NewAccountResponse();

		response.setId(account.getId());
		response.setCurrency(account.getCurrency());
		response.setName(account.getName());

		return response;
	}
}
