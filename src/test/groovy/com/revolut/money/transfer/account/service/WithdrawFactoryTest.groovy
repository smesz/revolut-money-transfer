package com.revolut.money.transfer.account.service

import com.revolut.money.transfer.currency.ExchangeRateService
import spock.lang.Specification
import spock.lang.Subject

class WithdrawFactoryTest extends Specification {

    def exchangeRateService = Mock(ExchangeRateService)

    @Subject withdrawExecutor = new WithdrawFactory(exchangeRateService)

    def 'Should create withdraw operation'() {
        when:
        def operation = withdrawExecutor.create(120.45, 'USD', 'EUR')

        then:
        1 * exchangeRateService.convertMoneyByExchangeRate(120.45, 'USD', 'EUR') >> 155.678

        and:
        operation.currency == 'USD'
        operation.amount == 120.45
        operation.amountInAccountCurrency == 155.678
    }
}
