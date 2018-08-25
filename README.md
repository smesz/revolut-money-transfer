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

When account is created, accountId will be generated in the response. User can use it to proceed with other account operations.

User can make deposits / withdraw operations with different currencies than accounts' currencies.
If account stores money in USD, user can still make deposit in EUR - money in EUR will be converted to USD, according to exchange rate EUR->USD in the system.

For testing purposes, application has only 2 exchange rates loaded in the DB:
* USD -> EUR
* EUR -> USD

### POST /account
Create account in the system.
Sample request:
```json
{
   "name": "account name",
   "currency": "USD"
}
```
Currency should be passed in capital letters.

Sample response:
```json
{
  "id": 1,
  "name": "account name",
  "currency": "USD"
}
```

### POST /account/{accountId}/deposit
Deposit money to the given account.

Sample request:
```json
{
  "amount": "1000.00",
  "currency": "EUR"
}
```
Amount value is passed as a String with 2 decimal places.

Sample response:
```json
{
    "account": "savings",
    "currentBalance": "1000.00",
    "status": {
        "code": "OK",
        "operation": "deposit",
        "transfer": {
            "amount": "1000.00",
            "currency": "EUR"
        }
    }
}
```

### POST /account/{accountId}/withdraw
Withdraw money from the given account.

Sample request:
```json
{
  "amount": "1000.00",
  "currency": "EUR"
}
```

Sample response:
```json
{
    "account": "savings",
    "currentBalance": "1000.00",
    "status": {
        "code": "OK",
        "operation": "withdraw",
        "transfer": {
            "amount": "1000.00",
            "currency": "EUR"
        }
    }
}
```

### GET /account/{accountId}/balance
Return current balance for the given account.

Sample request: no request body is sent as it's a GET operation
Sample response:
```json
{
    "account": "savings",
    "balance": "0.00",
    "currency": "EUR"
}
```

### POST /account/{fromAccountId}/transfer/{toAccountId}
Transfers money between accounts.

Sample request:
```json
{
  "amount": "200.00",
  "currency": "USD"
}
```

Sample response:
```json
{
  "from": {
    "account": "savings",
    "balance": "358.03",
    "currency": "USD"
  },
  "to": {
    "account": "savings_another",
    "balance": "200.00",
    "currency": "USD"
  },
  "status": {
    "code": "OK",
    "operation": "transfer",
    "transfer": {
      "amount": "200.00",
      "currency": "USD"
    }
  }
}
```

## Tests
Application code is covered with unit tests.
There are DB tests for DAO classes.

There is one huge integration test which tests all the interactions with /account REST endpoint - AccountResourceTest.groovy.

## Build and run
Application can be built with command
```
mvn clean package
```

Then, migrations to the database should be applied
```
java -jar target/transfers-1.0-SNAPSHOT.jar db migrate revolut-transfers.yml
```

Application can be run with command
```
java -jar target/transfers-1.0-SNAPSHOT.jar server revolut-transfers.yml
```
