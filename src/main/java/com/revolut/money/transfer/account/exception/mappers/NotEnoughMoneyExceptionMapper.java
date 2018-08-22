package com.revolut.money.transfer.account.exception.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.revolut.money.transfer.account.exception.NotEnoughMoneyException;

public class NotEnoughMoneyExceptionMapper implements ExceptionMapper<NotEnoughMoneyException> {

	@Override
	public Response toResponse(NotEnoughMoneyException e) {
		return Response.status(Status.NOT_ACCEPTABLE)
				.entity(e.getMessage())
				.type(MediaType.TEXT_PLAIN_TYPE)
				.build();
	}
}
