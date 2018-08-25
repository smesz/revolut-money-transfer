# Money Transfer REST API
REST API for money transfers between accounts.

## Technologies used
* Java 8
* Dropwizard
* Liquibase
* H2 in-memory DB
* JPA 2 / Hibernate
* Spock Framework

## API documentation
Application exposes following REST endpoints

| Http method | Endpoint                                        | Request                                           | Description                                                    |
|-------------|-------------------------------------------------|---------------------------------------------------|----------------------------------------------------------------|
| POST        | /account                                        | {   "name": "account name",   "currency": "USD" } | Create new account in the system                               |
| POST        | /account/{accountId}/deposit                    | {   "amount": "150.67",   "currency": "USD"  }    | Make deposit request - transfers money to the provided account |
| POST        | /account/{accountId}/withdraw                   | {   "amount": "100.00",   "currency": "EUR"  }    | Make withdraw request - take money out of provided account     |
| GET         | /account/{accountId}/balance                    |                                                   | Get account's balance                                          |
| POST        | /account/{fromAccountId}/transfer/{toAccountId} | {   "amount": "200.00",   "currency": "USD" }     | Money transfer request - moves money between accounts          |
