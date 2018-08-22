package com.revolut.money.transfer.account.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revolut.money.transfer.account.service.AccountService;
import com.revolut.money.transfer.model.dto.MoneyOperationRequest;
import com.revolut.money.transfer.model.dto.NewAccountRequest;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	private final AccountService accountService;

	public AccountResource(AccountService accountService) {
		this.accountService = accountService;
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
	public Response makeDeposit(@PathParam("accountId") long accountId, MoneyOperationRequest request) {
		return Response.ok(
				accountService.makeDeposit(accountId, request)
		).build();
	}

	@POST
	@Path("/{accountId}/withdraw")
	@UnitOfWork
	public Response makeWithdraw(@PathParam("accountId") long accountId, MoneyOperationRequest request) {
		return Response.ok(
				accountService.makeWithdraw(accountId, request)
		).build();
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
