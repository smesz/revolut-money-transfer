package com.revolut.money.transfer.person.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import com.revolut.money.transfer.model.Person;

import io.dropwizard.hibernate.AbstractDAO;

public class UserDao extends AbstractDAO<Person> {

	public UserDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public boolean exists(long id) {
		return findById(id).isPresent();
	}

	public Optional<Person> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

	public Person create(Person person) {
		return persist(person);
	}

	public List<Person> findAll() {
		return list(namedQuery("com.revolut.money.transfer.model.Person.findAll"));
	}
}
