package com.revolut.money.transfer;

import com.revolut.money.transfer.account.dao.AccountDao;
import com.revolut.money.transfer.account.rest.AccountResource;
import com.revolut.money.transfer.account.service.AccountConverter;
import com.revolut.money.transfer.account.service.AccountService;
import com.revolut.money.transfer.account.service.AccountValidator;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.AccountOperation;
import com.revolut.money.transfer.model.DepositOperation;
import com.revolut.money.transfer.model.Person;
import com.revolut.money.transfer.model.WithdrawOperation;
import com.revolut.money.transfer.person.dao.UserDao;
import com.revolut.money.transfer.person.exception.mappers.UserAlreadyExistsExceptionMapper;
import com.revolut.money.transfer.person.exception.mappers.UserDoesNotExistExceptionMapper;
import com.revolut.money.transfer.person.rest.UserResource;
import com.revolut.money.transfer.person.service.PersonConverter;
import com.revolut.money.transfer.person.service.UserService;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MoneyTransferApplication extends Application<MoneyTransferConfiguration> {

	private static final String APPLICATION_NAME = "revolut-transfers";

	private final HibernateBundle<MoneyTransferConfiguration> hibernate =
			new HibernateBundle<MoneyTransferConfiguration>(Person.class, Account.class, AccountOperation.class,
					DepositOperation.class, WithdrawOperation.class) {
				@Override
				public DataSourceFactory getDataSourceFactory(MoneyTransferConfiguration configuration) {
					return configuration.getDataSourceFactory();
				}
			};

	public static void main(String[] args) throws Exception {
		new MoneyTransferApplication().run(args);
	}

	@Override
	public String getName() {
		return APPLICATION_NAME;
	}

	@Override
	public void initialize(Bootstrap<MoneyTransferConfiguration> bootstrap) {
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(new MigrationsBundle<MoneyTransferConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(MoneyTransferConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});
	}

	public void run(MoneyTransferConfiguration configuration, Environment environment) {

		/*
		 * USER logic
		 */
		UserDao userDao = new UserDao(hibernate.getSessionFactory());
		PersonConverter personConverter = new PersonConverter();
		UserService userService = new UserService(userDao, personConverter);

		environment.jersey().register(
				new UserResource(userService, personConverter)
		);

		environment.jersey().register(new UserAlreadyExistsExceptionMapper());
		environment.jersey().register(new UserDoesNotExistExceptionMapper());

		/*
		 * ACCOUNT logic
		 */
		AccountDao accountDao = new AccountDao(hibernate.getSessionFactory());
		AccountConverter accountConverter = new AccountConverter();
		AccountValidator accountValidator = new AccountValidator(accountDao, userDao);
		AccountService accountService = new AccountService(accountDao, userService, accountConverter, accountValidator);

		environment.jersey().register(
				new AccountResource(accountService, accountConverter)
		);
	}
}
