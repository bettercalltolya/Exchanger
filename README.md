# API key

Api key for APILayer is provided via `local.properties`.

```
apiLayer.apiKey=*your api key*
```

# Currencies

Available currencies are hardcoded in `MockedCurrencyRepository`

# Initial balance

When database is created, `BalanceDao.checkAndInsertInitialBalance()` is executed to check if user have any non-empty balances. If not, it inserts a new balance of 1000 EUR.
This is a bit fragile approach as if EUR will be removed from available currencies, user will be stuck with empty balances
