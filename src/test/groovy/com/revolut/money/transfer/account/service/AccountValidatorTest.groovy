package com.revolut.money.transfer.account.service

import com.revolut.money.transfer.account.dao.AccountDao
import com.revolut.money.transfer.account.exception.AccountAlreadyExistsException
import com.revolut.money.transfer.model.dto.NewAccountRequest
import spock.lang.Specification
import spock.lang.Subject

class AccountValidatorTest extends Specification {

    def dao = Mock(AccountDao) {
        exists(1) >> false
        exists(2) >> false
        exists(3) >> true
    }

    @Subject validator = new AccountValidator(dao)

    def 'Should validate if request is acceptable to the system'() {
        when:
        validator.checkForAccountCreation(new NewAccountRequest(1, 'name', 'USD'))
        validator.checkForAccountCreation(new NewAccountRequest(2, 'name', 'USD'))

        then:
        noExceptionThrown()
    }

    def 'Should throw exception when account already exists'() {
        when:
        validator.checkForAccountCreation(new NewAccountRequest(3, 'name', 'USD'))

        then:
        thrown(AccountAlreadyExistsException)
    }
}
