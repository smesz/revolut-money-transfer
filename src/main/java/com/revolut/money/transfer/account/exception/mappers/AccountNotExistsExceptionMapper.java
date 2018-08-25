package com.revolut.money.transfer.account.exception.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.revolut.money.transfer.account.exception.AccountNotExistsException;
import com.revolut.money.transfer.model.GeneralError;

public class AccountNotExistsExceptionMapper implements ExceptionMapper<AccountNotExistsException> {

	@Override
	public Response toResponse(AccountNotExistsException e) {
		return Response.status(Status.NOT_FOUND)
				.entity(new GeneralError(e.getMessage()))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}
}
