# FianceHub

FinanceHub is a personal finance management application inspired by a spreadsheet I've been using for the past few years.

The project is built using:
- Kotlin
- Micronaut
- Java 21
- PostgreSQL


## Architecture
It follows the principles of Domain-Driven Design (DDD) using a Hexagonal Architecture (Ports and Adapters), which helps to keep the domain logic isolated from external concerns such as databases and APIs.

### Domain Design Diagram
```mermaid
---
title: FinanceHub
---
classDiagram
class Accounts {
    +uuid id
    +string name
    +int bankCode
    +int agency
    +int accountNumber
    +string pix
    +string loginUser
    AccountType accountType
    +string useDescription
    +bool active

    +create(account) uuid
    +update(account) account
    +getAll() List~account~
    +getAccount() account
}
class AccountType {
    <<enumeration>>
    +NOT_DEFINED
    +CHECKING_ACCOUNT
    +INVESTMENT
}
Accounts --> AccountType : reads


class Objectives {
    +uuid id
    +ObjectiveHorizon objectiveHorizon
    +Date estimatedDate
    +Date createdDate
    +Date updatedDate
    +string description
    +ObjectiveStatus status

    +create(objective) uuid
    +update(objective) objective
    +getAll() List~objective~
}
class ObjectiveHorizon {
    <<enumeration>>
    +SHORT_TERM
    +MEDIUM_TERM
    +LONG_TERM
}
class ObjectiveStatus {
    <<enumeration>>
    +ACTIVE
    +COMPLETED
    +CANCELLED
}
Objectives --> ObjectiveHorizon : reads
Objectives --> ObjectiveStatus : reads


class Asset {
    +uuid id
    +string name
    +AssetType type
    +string ticker
    +string country

    +create(asset) uuid
}
class AssetType {
    <<enumeration>>
    +STOCK
    +REIT
    +ETF
    +BOND
}
class AssetEarnings {
    +uuid id
    +uuid assetId
    +BigDecimal totalAmountReceived
    +YearMonth referenceMonth
    +string notes

    +createOrUpdateEarningEntry(earningEntry) uuid
    +listEarningsByAsset(asset) List~AssetEarnings~
}
Asset --> AssetEarnings : reads
Asset --> AssetType : reads

class IncomeEntry {
    +uuid id
    +YearMonth referenceMonth
    +BigDecimal grossAmount
    +BigDecimal discountAmount
    +BigDecimal netAmount
    +string description

    createOrUpdateIncome(entry) uuid
}
class ExpenseEntry {
    +uuid id
    +YearMonth referenceMonth
    +string category
    +BigDecimal amount
    +string description
    +bool isFixed

    createOrUpdateExpense(entry) uuid
}
class Balances {
    +uuid id
    +YearMonth referenceMonth
    +BigDecimal totalGrossIncomes
    +BigDecimal totalNetIncomes
    +BigDecimal totalFixExpenses
    +BigDecimal totalOtherExpenses
    +uuid incomeUuid
    +uuid expenseUuid

    createOrUpdateBalance(balance) uuid
    getBalances() List~balance~
    getLastBalance() balance
    getAnualBalance(referenceYear) balance
}
class InvestmentStrategies {
    +uuid id
    +int investableIncomePercent
    +int fixedIncomePercent
    +int reitsPercent
    +int stocksPercent
    +int cryptoPercent
    +int internationalPercent
    +string description
    +Date createdDate
    +Date updatedDate
    +bool active

    +create(strategy) uuid
    +update(strategy) strategy
    +getActive() strategy
}
class InvestmentsEntry {
    +uuid id
    +YearMonth referenceMonth
    +string name
    +BigDecimal amount
    +InvestmentType type
    +string description

    createOrUpdateinvestment(investment) uuid
}
class InvestmentType {
    <<enumeration>>
    +FIXED_INCOME,
    +VARIABLE_INCOME,
    +EMERGENCY_FUND
}
class InvestmentsSnapshot {
    +uuid id
    +YearMonth referenceMonth
    +BigDecimal totalInvested
    +BigDecimal fixedEmergencyFundValue
    +BigDecimal fixedIncomeValue
    +BigDecimal variableIncomeValue
    +string notes

    getInvestmentSnapshot() List~investmentSnapshot~
    getLastInvestmentSnapshot() investmentSnapshot
}
IncomeEntry --> Balances : creates
IncomeEntry <-- Balances : reads
ExpenseEntry --> Balances : creates
ExpenseEntry <-- Balances : reads
Balances --> InvestmentStrategies : reads
Balances --> InvestmentsSnapshot : reads
Balances <-- InvestmentsSnapshot : creates
InvestmentsEntry --> InvestmentType : reads
InvestmentsEntry --> InvestmentsSnapshot : creates



```

### Project architecture
````
financehub/
├── domains/
│   ├── accounts/
│   │   ├── adapters/
│   │   │   ├── input/
│   │   │   │   └── rest/
│   │   │   │       └── AccountController.kt
│   │   │   └── output/
│   │   │       └── repository/
│   │   │           └── database/
│   │   │               └── AccountRepository.kt
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   └── Account.kt
│   │   │   └── usecase/
│   │   │       └── AccountUseCase.kt
│   │   └── ports/
│   │       ├── input/
│   │       │   └── AccountServicePort.kt
│   │       └── output/
│   │           └── AccountRepositoryPort.kt
│   ├── objectives/
│   │   ├── adapters/
│   │   │   └── ...
│   │   ├── domain/
│   │   │   └── ...
│   │   └── ports/
│   │       └── ...
│   ├── assets/
│   │   ├── adapters/
│   │   │   └── ...
│   │   ├── domain/
│   │   │   └── ...
│   │   └── ports/
│   │       └── ...
````

- domain/ – Contains the core business logic, including entities and use cases.
- ports/input/ – Defines the input contracts (interfaces) for the application's use cases.
- ports/output/ – Declares output contracts for external dependencies such as databases, caches and APIs.
- adapters/input/ – Implements entry points like REST controllers, CLI commands, or event listeners.
- adapters/output/ – Implements integrations with external systems such as databases, caches, and APIs.

### Importing sample data
There is an importer script under `/scripts/financehub-importer` built in Golang
- To import data:
  - Create a file `/scripts/financehub-importer/resources/financehub_import.xlsx` using `financehub_import-sample.xlsx` as example;
  - Run `make up`
  - Run `make import`
