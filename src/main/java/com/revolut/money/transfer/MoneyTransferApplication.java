package com.revolut.money.transfer;

import com.revolut.money.transfer.account.dao.AccountDao;
import com.revolut.money.transfer.account.rest.AccountResource;
import com.revolut.money.transfer.account.service.AccountConverter;
import com.revolut.money.transfer.account.service.AccountService;
import com.revolut.money.transfer.account.service.AccountValidator;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.AccountOperation;
import com.revolut.money.transfer.model.DepositOperation;
import com.revolut.money.transfer.model.WithdrawOperation;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MoneyTransferApplication extends Application<MoneyTransferConfiguration> {

	private static final String APPLICATION_NAME = "revolut-transfers";

	private final HibernateBundle<MoneyTransferConfiguration> hibernate =
			new HibernateBundle<MoneyTransferConfiguration>(Account.class, AccountOperation.class,
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
		 * ACCOUNT logic
		 */
		AccountDao accountDao = new AccountDao(hibernate.getSessionFactory());
		AccountConverter accountConverter = new AccountConverter();
		AccountValidator accountValidator = new AccountValidator(accountDao);
		AccountService accountService = new AccountService(accountDao, accountConverter, accountValidator);

		environment.jersey().register(
				new AccountResource(accountService, accountConverter)
		);
	}
}
