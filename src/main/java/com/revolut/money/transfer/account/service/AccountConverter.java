package com.revolut.money.transfer.account.service;

import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Person;
import com.revolut.money.transfer.model.account.NewAccountRequest;
import com.revolut.money.transfer.model.account.NewAccountResponse;
import com.revolut.money.transfer.model.account.NewAccountResponse.Owner;

public class AccountConverter {

	public Account fromRequest(NewAccountRequest request, Person accountOwner) {
		return new Account(
				request.getId(),
				request.getName(),
				request.getCurrency(),
				accountOwner
		);
	}

	public NewAccountResponse toResponse(Account account) {
		NewAccountResponse response = new NewAccountResponse();

		Owner owner = new Owner();
		owner.setId(account.getOwner().getId());
		owner.setFirstName(account.getOwner().getFirstName());
		owner.setLastName(account.getOwner().getLastName());

		response.setId(account.getId());
		response.setCurrency(account.getCurrency());
		response.setName(account.getName());
		response.setOwner(owner);

		return response;
	}
}
