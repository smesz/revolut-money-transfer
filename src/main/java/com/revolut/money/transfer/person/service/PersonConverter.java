package com.revolut.money.transfer.person.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Person;
import com.revolut.money.transfer.model.dto.PersonDto;

public class PersonConverter {

	public List<PersonDto> toDto(List<Person> persons) {
		return persons.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	public PersonDto toDto(Person person) {
		PersonDto dto = new PersonDto();

		dto.setId(person.getId());
		dto.setFirstName(person.getFirstName());
		dto.setLastName(person.getLastName());
		dto.setEmail(person.getEmail());
		dto.setAccountIds(accountIds(person));

		return dto;
	}

	private List<String> accountIds(Person person) {
		return person.getAccounts().stream()
				.map(Account::getId)
				.map(Objects::toString)
				.collect(Collectors.toList());
	}

	public Person fromDto(PersonDto dto) {
		return new Person(
				dto.getId(),
				dto.getFirstName(),
				dto.getLastName(),
				dto.getEmail()
		);
	}
}
