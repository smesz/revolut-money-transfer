package com.revolut.money.transfer.account.service

import com.revolut.money.transfer.model.account.Account
import com.revolut.money.transfer.model.dto.NewAccountRequest
import spock.lang.Specification
import spock.lang.Subject

class AccountConverterTest extends Specification {

    @Subject converter = new AccountConverter()

    def 'Should convert request to Account instance'() {
        when:
        def account = converter.fromRequest(new NewAccountRequest(1, 'account', 'USD'))

        then:
        account.id == 1
        account.name == 'account'
        account.currency == 'USD'
    }

    def 'Should convert Account object to response'() {
        when:
        def response = converter.toResponse(new Account(1, 'name', 'EUR'))

        then:
        response.id == 1
        response.name == 'name'
        response.currency == 'EUR'
    }
}
