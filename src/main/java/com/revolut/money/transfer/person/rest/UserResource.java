package com.revolut.money.transfer.person.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revolut.money.transfer.model.Person;
import com.revolut.money.transfer.model.dto.PersonDto;
import com.revolut.money.transfer.person.service.PersonConverter;
import com.revolut.money.transfer.person.service.UserService;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private final UserService userService;
	private final PersonConverter converter;

	public UserResource(UserService userService, PersonConverter converter) {
		this.userService = userService;
		this.converter = converter;
	}

	@POST
	@UnitOfWork
	public Response createUser(PersonDto personDto) {
		Person person = userService.createPerson(personDto);
		return Response.ok(converter.toDto(person)).build();
	}

	@GET
	@Path("/{personId}")
	@UnitOfWork
	public Response getUser(@PathParam("personId") long id) {
		Person person = userService.getUser(id);
		return Response.ok(converter.toDto(person)).build();
	}

	@GET
	@UnitOfWork
	public Response getAllUsers() {
		List<Person> users = userService.getAllUsers();
		return Response.ok(converter.toDto(users)).build();
	}
}
