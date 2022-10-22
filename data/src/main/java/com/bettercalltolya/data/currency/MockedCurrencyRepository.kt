package com.bettercalltolya.data.currency

import com.bettercalltolya.domain.currency.CurrencyRepository

class MockedCurrencyRepository : CurrencyRepository {

    private val currencies = setOf("EUR", "USD", "GBP", "JPY")

    override fun getAvailableCurrencies(): Set<String> = currencies
}
