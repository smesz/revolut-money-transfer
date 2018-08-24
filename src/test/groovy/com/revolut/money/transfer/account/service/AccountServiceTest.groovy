package com.revolut.money.transfer.account.service

import com.revolut.money.transfer.account.dao.AccountDao
import com.revolut.money.transfer.account.exception.AccountAlreadyExistsException
import com.revolut.money.transfer.account.exception.AccountNotExistsException
import com.revolut.money.transfer.model.account.Account
import com.revolut.money.transfer.model.account.DepositOperation
import com.revolut.money.transfer.model.account.WithdrawOperation
import com.revolut.money.transfer.model.dto.MoneyOperationRequest
import com.revolut.money.transfer.model.dto.NewAccountRequest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class AccountServiceTest extends Specification {

    @Shared ACCOUNT = new Account(1, 'name', 'USD')

    def dao = Mock(AccountDao) {
        exists(1) >> true
        findById(1) >> ACCOUNT
        getOrThrowException(1) >> ACCOUNT

        getOrThrowException(2) >> { throw new AccountNotExistsException(2) }
    }

    def converter = new AccountConverter()
    def validator = Mock(AccountValidator)
    def depositFactory = Mock(DepositFactory)
    def withdrawFactory = Mock(WithdrawFactory)

    @Subject service = new AccountService(dao, converter, validator, depositFactory, withdrawFactory)

    def 'Should create new account'() {
        given:
        def request = new NewAccountRequest(1, 'name', 'USD')

        when:
        def response = service.createAccount(request)

        then:
        1 * validator.checkForAccountCreation(request)
        1 * dao.create(_ as Account) >> ACCOUNT

        and:
        response.id == 1
        response.name == 'name'
        response.currency == 'USD'
    }

    def 'Should not create new account if already exists one with the same id'() {
        given:
        def request = new NewAccountRequest(1, 'name', 'USD')

        validator.checkForAccountCreation(request) >> { throw new AccountAlreadyExistsException(1) }

        when:
        service.createAccount(request)

        then:
        thrown(AccountAlreadyExistsException)
    }

    def 'Should make a deposit for account'() {
        given:
        def request = new MoneyOperationRequest('300.50', 'USD')

        when:
        def response = service.makeDeposit(1, request)

        then:
        1 * depositFactory.create(300.50, 'USD', 'USD') >> new DepositOperation(300.50, 300.50, 'USD', new Date())

        and:
        response.account == 'name'
        response.operation == 'deposit'
        response.currentBalance == '300.50'
        response.money.amount == '300.50'
        response.money.currency == 'USD'
        response.status.code == 'OK'
    }

    def 'Should return error when making deposit fails - no account'() {
        given:
        def request = new MoneyOperationRequest('300.50', 'USD')

        when:
        def response = service.makeDeposit(2, request)

        then:
        0 * depositFactory._

        and:
        response.account == ''
        response.operation == 'deposit'
        response.currentBalance == ''
        response.money.amount == '300.50'
        response.money.currency == 'USD'
        response.status.code == 'ERROR'
    }

    def 'Should make a withdraw from the account'() {
        given:
        def account = new Account(10, 'name', 'USD')
        account.accountOperations << new DepositOperation(1000.0, 1000.0, 'USD', new Date())

        def request = new MoneyOperationRequest('300.50', 'USD')

        when:
        def response = service.makeWithdraw(10, request)

        then:
        1 * dao.getOrThrowException(10) >> account
        1 * withdrawFactory.create(300.50, 'USD', 'USD') >> new WithdrawOperation(300.50, 300.50, 'USD', new Date())

        and:
        response.account == 'name'
        response.operation == 'withdraw'
        response.currentBalance == '699.50'
        response.money.amount == '300.50'
        response.money.currency == 'USD'
        response.status.code == 'OK'
    }

    def 'Withdraw should fail due to not enough money'() {
        given:
        def account = new Account(10, 'name', 'USD')

        when:
        account.makeDeposit(new DepositOperation(1000.0, 1000.0, 'USD', new Date()))
        def balance = account.getBalance()

        then:
        balance == 1000.0

        when:
        def response = service.makeWithdraw(10, new MoneyOperationRequest('1100.0', 'USD'))

        then:
        1 * dao.getOrThrowException(10) >> account
        1 * withdrawFactory.create(1100.0, 'USD', 'USD') >> new WithdrawOperation(1100.0, 1100.0, 'USD', new Date())

        and:
        response.account == 'name'
        response.operation == 'withdraw'
        response.currentBalance == '1000.00'
        response.money.amount == '1100.0'
        response.money.currency == 'USD'
        response.status.code == 'ERROR'
        response.status.details == "Not enough money on account '10'"
    }

    def 'Should get balance of the account'() {
        given:
        def account = new Account(1, 'name', 'USD')

        account.makeDeposit(new DepositOperation(150.0, 150.0, 'USD', new Date()))
        account.makeDeposit(new DepositOperation(150.0, 211.5, 'EUR', new Date()))

        when:
        def response = service.getBalance(1)

        then:
        1 * dao.getOrThrowException(1) >> account

        and:
        response.account == 'name'
        response.balance == '361.50'
        response.currency == 'USD'
    }

    def 'Should throw exception when account not found'() {
        when:
        service.getBalance(2)

        then:
        thrown(AccountNotExistsException)
    }
}
