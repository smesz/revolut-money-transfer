package com.revolut.money.transfer.person.exception.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.revolut.money.transfer.person.exception.UserDoesNotExistException;

public class UserDoesNotExistExceptionMapper implements ExceptionMapper<UserDoesNotExistException> {

	@Override
	public Response toResponse(UserDoesNotExistException e) {
		return Response.status(Status.NOT_FOUND)
				.entity(e.getMessage())
				.type(MediaType.TEXT_PLAIN_TYPE)
				.build();
	}
}
