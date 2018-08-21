package com.revolut.money.transfer.person.exception.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.revolut.money.transfer.person.exception.UserAlreadyExistsException;

public class UserAlreadyExistsExceptionMapper implements ExceptionMapper<UserAlreadyExistsException> {

	@Override
	public Response toResponse(UserAlreadyExistsException e) {
		return Response.status(Status.CONFLICT)
				.entity(e.getMessage())
				.type(MediaType.TEXT_PLAIN_TYPE)
				.build();
	}
}
