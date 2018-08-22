package com.revolut.money.transfer.account.dao;

import org.hibernate.SessionFactory;

import com.revolut.money.transfer.model.account.Account;

import io.dropwizard.hibernate.AbstractDAO;

public class AccountDao extends AbstractDAO<Account> {

	public AccountDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public boolean exists(long id) {
		return findById(id) != null;
	}

	public Account findById(long id) {
		return get(id);
	}

	public Account create(Account account) {
		return persist(account);
	}
}
