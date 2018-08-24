package com.revolut.money.transfer.account.service

import com.revolut.money.transfer.account.dao.AccountDao
import com.revolut.money.transfer.account.exception.AccountNotExistsException
import com.revolut.money.transfer.model.account.Account
import com.revolut.money.transfer.model.account.DepositOperation
import com.revolut.money.transfer.model.account.WithdrawOperation
import com.revolut.money.transfer.model.dto.MoneyOperationRequest
import com.revolut.money.transfer.model.dto.NewAccountRequest
import spock.lang.Specification
import spock.lang.Subject

class AccountServiceTest extends Specification {

    def account = account(1, 'name', 'USD')

    def dao = Mock(AccountDao) {
        exists(1) >> true
        findById(1) >> account
        getOrThrowException(1) >> account

        getOrThrowException(2) >> { throw new AccountNotExistsException(2) }
    }

    def converter = new AccountConverter()
    def depositFactory = Mock(DepositFactory)
    def withdrawFactory = Mock(WithdrawFactory)

    @Subject service = new AccountService(dao, converter, depositFactory, withdrawFactory)

    def 'Should create new account'() {
        given:
        def request = new NewAccountRequest('name', 'USD')

        when:
        def response = service.createAccount(request)

        then:
        1 * dao.create(_ as Account) >> account

        and:
        response.id == 1
        response.name == 'name'
        response.currency == 'USD'
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
        account.accountOperations << new DepositOperation(1000.0, 1000.0, 'USD', new Date())

        def request = new MoneyOperationRequest('300.50', 'USD')

        when:
        def response = service.makeWithdraw(1, request)

        then:
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
        when:
        account.makeDeposit(new DepositOperation(1000.0, 1000.0, 'USD', new Date()))
        def balance = account.getBalance()

        then:
        balance == 1000.0

        when:
        def response = service.makeWithdraw(1, new MoneyOperationRequest('1100.0', 'USD'))

        then:
        1 * withdrawFactory.create(1100.0, 'USD', 'USD') >> new WithdrawOperation(1100.0, 1100.0, 'USD', new Date())

        and:
        response.account == 'name'
        response.operation == 'withdraw'
        response.currentBalance == '1000.00'
        response.money.amount == '1100.0'
        response.money.currency == 'USD'
        response.status.code == 'ERROR'
        response.status.details == "Not enough money on account '1'"
    }

    def 'Should get balance of the account'() {
        given:
        account.makeDeposit(new DepositOperation(150.0, 150.0, 'USD', new Date()))
        account.makeDeposit(new DepositOperation(150.0, 211.5, 'EUR', new Date()))

        when:
        def response = service.getBalance(1)

        then:
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

    def account(id, name, currency) {
        def account = new Account(name, currency)
        account.id = id

        account
    }
}
