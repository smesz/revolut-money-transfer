package com.revolut.money.transfer.account.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revolut.money.transfer.account.service.AccountConverter;
import com.revolut.money.transfer.account.service.AccountService;
import com.revolut.money.transfer.model.account.MoneyOperationRequest;
import com.revolut.money.transfer.model.account.NewAccountRequest;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	private final AccountService accountService;
	private final AccountConverter converter;

	public AccountResource(AccountService accountService, AccountConverter converter) {
		this.accountService = accountService;
		this.converter = converter;
	}

	@POST
	@UnitOfWork
	public Response createAccount(NewAccountRequest newAccountRequest) {
		return Response.ok(
				accountService.createAccount(newAccountRequest)
		).build();
	}

	@POST
	@Path("/{accountId}/deposit")
	@UnitOfWork
	public Response makeDeposit(@PathParam("accountId") long accountId, MoneyOperationRequest moneyOperationRequest) {
		return Response.ok(
				accountService.makeDeposit(accountId, moneyOperationRequest)
		).build();
	}

	@POST
	@Path("/{accountId}/withdraw")
	@UnitOfWork
	public Response makeWithdraw(@PathParam("accountId") long accountId, MoneyOperationRequest moneyOperationRequest) {
		accountService.makeWithdraw(accountId, moneyOperationRequest);

		return Response.ok().build();
	}

	@GET
	@Path("/{accountId}/balance")
	@UnitOfWork
	public Response getBalance(@PathParam("accountId") long accountId) {
		return Response.ok(
				accountService.getBalance(accountId)
		).build();
	}
}
