package com.revolut.money.transfer.currency.dao

import com.revolut.money.transfer.model.rates.ExchangeRate
import io.dropwizard.testing.junit.DAOTestRule
import org.junit.Rule
import spock.lang.Specification
import spock.lang.Subject

class ExchangeRateDaoTest extends Specification {

    @Rule
    public DAOTestRule database = DAOTestRule.newBuilder()
            .addEntityClass(ExchangeRate.class)
            .build()

    @Subject dao = new ExchangeRateDao(database.getSessionFactory())

    def 'Should retrieve persisted exchange rate'() {
        when:
        def rate = dao.save('USD', 'EUR', 1.15)

        then:
        assertExchangeRate(rate, 'USD', 'EUR', 1.15)

        when:
        rate = dao.get('USD', 'EUR')

        then:
        assertExchangeRate(rate, 'USD', 'EUR', 1.15)
    }

    def 'Should return null if no exchange rate in database'() {
        expect:
        dao.get('PLN', 'EUR') == null
    }

    void assertExchangeRate(ExchangeRate exchangeRate, fromCurrency, toCurrency, rate) {
        assert exchangeRate.id.fromCurrency == fromCurrency
        assert exchangeRate.id.toCurrency == toCurrency
        assert exchangeRate.exchangeRate == rate
    }
}
