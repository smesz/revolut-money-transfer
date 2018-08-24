package com.revolut.money.transfer.account.exception.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.revolut.money.transfer.account.exception.AccountNotExistsException;

public class AccountNotExistsExceptionMapper implements ExceptionMapper<AccountNotExistsException> {

	@Override
	public Response toResponse(AccountNotExistsException e) {
		return Response.status(Status.NOT_FOUND)
				.entity(e.getMessage())
				.type(MediaType.TEXT_PLAIN_TYPE)
				.build();
	}
}
