package com.revolut.money.transfer.account.exception.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.revolut.money.transfer.account.exception.AccountAlreadyExistsException;

public class AccountAlreadyExistsExceptionMapper implements ExceptionMapper<AccountAlreadyExistsException> {

	@Override
	public Response toResponse(AccountAlreadyExistsException e) {
		return Response.status(Status.CONFLICT)
				.entity(e.getMessage())
				.type(MediaType.TEXT_PLAIN_TYPE)
				.build();
	}
}
