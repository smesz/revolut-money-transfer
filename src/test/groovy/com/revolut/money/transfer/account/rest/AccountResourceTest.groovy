package com.revolut.money.transfer.account.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.revolut.money.transfer.MoneyTransferApplication
import com.revolut.money.transfer.MoneyTransferConfiguration
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
    }

    def 'Should create account with success'() {
        when:
        def response = createAccount('account/create/input/account_create_ok.json')

        then:
        response.status == 200
        assertResponse(response.readEntity(String), jsonFromFile('account/create/output/account_create_ok.json'))
    }

    def 'Should return error response due to account already exists'() {
        when:
        def response = createAccount('account/create/input/account_create_error_already_exists.json')

        then:
        response.status == 409
        assertResponse(response.readEntity(String), jsonFromFile('account/create/output/account_create_error_already_exists.json'))
    }

    def createAccount(String requestJsonPath) {
        RULE.client()
                .target("http://localhost:${RULE.getLocalPort()}/account")
                .request()
                .post(Entity.json(jsonFromFile(requestJsonPath)))
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
