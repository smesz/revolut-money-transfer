package com.revolut.money.transfer.account.service

import com.revolut.money.transfer.model.account.Account
import com.revolut.money.transfer.model.dto.NewAccountRequest
import spock.lang.Specification
import spock.lang.Subject

class AccountConverterTest extends Specification {

    @Subject converter = new AccountConverter()

    def 'Should convert request to Account instance'() {
        when:
        def account = converter.fromRequest(new NewAccountRequest('account', 'USD'))

        then:
        account.name == 'account'
        account.currency == 'USD'
    }

    def 'Should convert Account object to response'() {
        given:
        def account = new Account('name', 'EUR')
        account.id = 1

        when:
        def response = converter.toResponse(account)

        then:
        response.id == 1
        response.name == 'name'
        response.currency == 'EUR'
    }
}
