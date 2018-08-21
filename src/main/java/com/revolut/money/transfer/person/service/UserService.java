package com.revolut.money.transfer.person.service;

import java.util.List;

import com.revolut.money.transfer.model.Person;
import com.revolut.money.transfer.model.dto.PersonDto;
import com.revolut.money.transfer.person.dao.UserDao;
import com.revolut.money.transfer.person.exception.UserAlreadyExistsException;
import com.revolut.money.transfer.person.exception.UserDoesNotExistException;

public class UserService {

	private final UserDao userDao;
	private final PersonConverter converter;

	public UserService(UserDao userDao, PersonConverter converter) {
		this.userDao = userDao;
		this.converter = converter;
	}

	public Person createPerson(PersonDto person) {
		if (userExists(person.getId())) {
			throw new UserAlreadyExistsException(person.getId());
		}

		return userDao.create(converter.fromDto(person));
	}

	public Person getUser(long id) {
		return userDao.findById(id).orElseThrow(() -> new UserDoesNotExistException(id));
	}

	public List<Person> getAllUsers() {
		return userDao.findAll();
	}

	private boolean userExists(long id) {
		return userDao.exists(id);
	}
}
