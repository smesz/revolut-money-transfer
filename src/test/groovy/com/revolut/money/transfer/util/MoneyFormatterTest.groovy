package com.revolut.money.transfer.util

import spock.lang.Specification
import spock.lang.Unroll


class MoneyFormatterTest extends Specification {

    @Unroll
    def 'Should format money amount #amount to 5 digit precision string value'() {
        expect:
        MoneyFormatter.format(amount) == expected

        where:
        amount     | expected
        13.45      | '13.45'
        678.3456   | '678.34'
        10.3456789 | '10.34'
        1000.0     | '1000.00'
    }

    @Unroll
    def 'Should parse string money value to double'() {
        expect:
        MoneyFormatter.parse(amount) == expected

        where:
        amount      | expected
        '12.50'     | 12.5
        '10.00'     | 10
        '10.345678' | 10.34567
    }
}
