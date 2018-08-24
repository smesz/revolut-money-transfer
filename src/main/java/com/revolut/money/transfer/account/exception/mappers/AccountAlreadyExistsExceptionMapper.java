package com.revolut.money.transfer.account.exception.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.revolut.money.transfer.account.exception.AccountAlreadyExistsException;
import com.revolut.money.transfer.model.GeneralError;

public class AccountAlreadyExistsExceptionMapper implements ExceptionMapper<AccountAlreadyExistsException> {

	@Override
	public Response toResponse(AccountAlreadyExistsException e) {
		return Response.status(Status.CONFLICT)
				.entity(new GeneralError(e.getMessage()))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}
}
