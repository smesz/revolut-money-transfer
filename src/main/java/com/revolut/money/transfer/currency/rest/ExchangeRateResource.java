package com.revolut.money.transfer.currency.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revolut.money.transfer.currency.dao.ExchangeRateDao;
import com.revolut.money.transfer.model.dto.ExchangeRateDto;
import com.revolut.money.transfer.model.rates.ExchangeRate;
import com.revolut.money.transfer.util.MoneyFormatter;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/exchangerate")
@Produces(MediaType.APPLICATION_JSON)
public class ExchangeRateResource {

	private final ExchangeRateDao dao;

	public ExchangeRateResource(ExchangeRateDao dao) {
		this.dao = dao;
	}

	@POST
	@UnitOfWork
	public Response addExchangeRate(ExchangeRateDto request) {
		ExchangeRate exchangeRate =
				dao.save(request.getFromCurrency(), request.getToCurrency(), MoneyFormatter.parse(request.getRate()));

		return Response.ok(exchangeRate).build();
	}

}
