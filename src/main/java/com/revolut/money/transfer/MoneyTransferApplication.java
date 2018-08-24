package com.revolut.money.transfer;

import com.revolut.money.transfer.account.dao.AccountDao;
import com.revolut.money.transfer.account.exception.mappers.AccountAlreadyExistsExceptionMapper;
import com.revolut.money.transfer.account.exception.mappers.NotEnoughMoneyExceptionMapper;
import com.revolut.money.transfer.account.rest.AccountResource;
import com.revolut.money.transfer.account.service.AccountConverter;
import com.revolut.money.transfer.account.service.AccountService;
import com.revolut.money.transfer.account.service.AccountValidator;
import com.revolut.money.transfer.account.service.DepositFactory;
import com.revolut.money.transfer.account.service.WithdrawFactory;
import com.revolut.money.transfer.currency.ExchangeRateService;
import com.revolut.money.transfer.currency.MoneyExchangeRateService;
import com.revolut.money.transfer.currency.dao.ExchangeRateDao;
import com.revolut.money.transfer.model.account.Account;
import com.revolut.money.transfer.model.account.AccountOperation;
import com.revolut.money.transfer.model.account.DepositOperation;
import com.revolut.money.transfer.model.account.WithdrawOperation;
import com.revolut.money.transfer.model.rates.ExchangeRate;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MoneyTransferApplication extends Application<MoneyTransferConfiguration> {

	private static final String APPLICATION_NAME = "revolut-transfers";

	private final HibernateBundle<MoneyTransferConfiguration> hibernate =
			new HibernateBundle<MoneyTransferConfiguration>(Account.class, AccountOperation.class, ExchangeRate.class,
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

		ExchangeRateService exchangeRateService = new MoneyExchangeRateService(
				new ExchangeRateDao(hibernate.getSessionFactory())
		);

		DepositFactory depositFactory = new DepositFactory(exchangeRateService);
		WithdrawFactory withdrawFactory = new WithdrawFactory(exchangeRateService);

		AccountService accountService = new AccountService(accountDao, accountConverter, accountValidator,
				depositFactory, withdrawFactory);

		environment.jersey().register(new AccountResource(accountService));
		environment.jersey().register(new AccountAlreadyExistsExceptionMapper());
		environment.jersey().register(new NotEnoughMoneyExceptionMapper());
	}
}
