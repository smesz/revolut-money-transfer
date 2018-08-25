package com.revolut.money.transfer.account.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.revolut.money.transfer.MoneyTransferApplication
import com.revolut.money.transfer.MoneyTransferConfiguration
import com.revolut.money.transfer.model.dto.ExchangeRateDto
import groovy.json.JsonSlurper
import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit.DropwizardAppRule
import org.junit.ClassRule
import spock.lang.Specification
import spock.lang.Stepwise

import javax.ws.rs.client.Entity

@Stepwise
class AccountResourceTest extends Specification {

    def objectMapper = new ObjectMapper()
    def jsonSlurper = new JsonSlurper()

    @ClassRule
    public static final DropwizardAppRule<MoneyTransferConfiguration> RULE =
            new DropwizardAppRule<MoneyTransferConfiguration>(MoneyTransferApplication.class, ResourceHelpers.resourceFilePath('revolut-transfers-test.yml'))

    def setupSpec() {
        RULE.testSupport.before()
        RULE.application.run()

        addExchangeRate('EUR', 'USD', '1.15874')
    }

    def 'Should create account 1 with success - USD'() {
        when:
        def response = createAccount('account/create/input/account_create_ok_1.json')

        then:
        response.status == 200
        assertResponse(response.readEntity(String), jsonFromFile('account/create/output/account_create_ok_1.json'))
    }

    def 'Should create account 2 with success - USD'() {
        when:
        def response = createAccount('account/create/input/account_create_ok_2.json')

        then:
        response.status == 200
        assertResponse(response.readEntity(String), jsonFromFile('account/create/output/account_create_ok_2.json'))
    }

    def 'Should make deposit on account 1 with 500.10 USD'() {
        when:
        def response = makeDeposit(1, 'account/deposit/input/account_deposit_ok_1.json')

        then:
        response.status == 200
        assertResponse(response.readEntity(String), jsonFromFile('account/deposit/output/account_deposit_ok_1.json'))
    }

    def 'Should make another deposit on account 1 with 200 EUR'() {
        when: '200 EUR = 231,748 USD'
        def response = makeDeposit(1, 'account/deposit/input/account_deposit_ok_1_eur.json')

        then:
        response.status == 200
        assertResponse(response.readEntity(String), jsonFromFile('account/deposit/output/account_deposit_ok_1_eur.json'))
    }

    def createAccount(String requestJsonPath) {
        RULE.client()
                .target("http://localhost:${RULE.getLocalPort()}/account")
                .request()
                .post(Entity.json(jsonFromFile(requestJsonPath)))
    }

    def makeDeposit(accountId, String requestJsonPath) {
        RULE.client()
                .target("http://localhost:${RULE.getLocalPort()}/account/${accountId}/deposit")
                .request()
                .post(Entity.json(jsonFromFile(requestJsonPath)))
    }

    def addExchangeRate(fromCurrency, toCurrency, rate) {
        RULE.client()
                .target("http://localhost:${RULE.getLocalPort()}/exchangerate")
                .request()
                .post(Entity.json(new ExchangeRateDto(fromCurrency, toCurrency, rate)))
    }

    def jsonFromFile(String path) throws IOException {
        return objectMapper
                .readTree(getClass().getClassLoader().getResource(path))
                .toString()
    }

    void assertResponse(String result, String expected) {
        assert jsonSlurper.parseText(result) == jsonSlurper.parseText(expected)
    }
}
