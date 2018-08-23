package com.revolut.money.transfer.currency

import com.revolut.money.transfer.currency.dao.ExchangeRateDao
import com.revolut.money.transfer.model.rates.ExchangeRate
import com.revolut.money.transfer.model.rates.ExchangeRateKey
import spock.lang.Specification
import spock.lang.Subject

class MoneyExchangeRateServiceTest extends Specification {

    def dao = Mock(ExchangeRateDao)

    @Subject exchangeRateService = new MoneyExchangeRateService(dao)

    def 'Should call DAO object and retrieve exchange rate'() {
        given:
        def rate = 1.456789
        dao.get('USD', 'EUR') >> new ExchangeRate(new ExchangeRateKey('USD', 'EUR'), rate)

        expect:
        exchangeRateService.getExchangeRate('USD', 'EUR') == rate
    }
}
