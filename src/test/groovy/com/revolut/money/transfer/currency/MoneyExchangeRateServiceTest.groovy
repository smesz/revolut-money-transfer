package com.revolut.money.transfer.currency

import com.revolut.money.transfer.currency.dao.ExchangeRateDao
import com.revolut.money.transfer.model.rates.ExchangeRate
import com.revolut.money.transfer.model.rates.ExchangeRateKey
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class MoneyExchangeRateServiceTest extends Specification {

    def dao = Mock(ExchangeRateDao) {
        get('USD', 'EUR') >> new ExchangeRate(new ExchangeRateKey('USD', 'EUR'), 1.55)
        get('PLN', 'EUR') >> new ExchangeRate(new ExchangeRateKey('PLN', 'EUR'), 4.3215)
        get('PLN', 'USD') >> new ExchangeRate(new ExchangeRateKey('PLN', 'USD'), 3.56789)
    }

    @Subject exchangeRateService = new MoneyExchangeRateService(dao)

    def 'Should get exchange rate for given pair of currencies'() {
        expect:
        exchangeRateService.getExchangeRate('USD', 'EUR') == 1.55
        exchangeRateService.getExchangeRate('PLN', 'EUR') == 4.3215
    }

    def 'Should return 1 if provided currencies are the same'() {
        expect:
        exchangeRateService.getExchangeRate('PLN', 'PLN') == 1
    }

    @Unroll
    def 'Should convert given amount using exchange rate'() {
        expect:
        exchangeRateService.convertMoneyByExchangeRate(amount, from, to) == expected

        where:
        amount | from  | to    || expected
        100.0  | 'USD' | 'EUR' || 155.0
        100.0  | 'PLN' | 'EUR' || 432.15
        777.0  | 'PLN' | 'USD' || 2772.25053
    }
}
