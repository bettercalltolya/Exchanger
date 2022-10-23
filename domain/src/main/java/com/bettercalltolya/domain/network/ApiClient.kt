package com.bettercalltolya.domain.network

import com.bettercalltolya.domain.model.ExchangeRates

interface ApiClient {

    suspend fun getExchangeRates(
        baseCurrency: String,
        targetCurrencies: List<String>
    ): ExchangeRates
}
