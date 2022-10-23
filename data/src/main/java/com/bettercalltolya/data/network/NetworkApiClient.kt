package com.bettercalltolya.data.network

import com.bettercalltolya.data.api.RatesApiService
import com.bettercalltolya.domain.model.ExchangeRates
import com.bettercalltolya.domain.network.ApiClient

class NetworkApiClient(
    private val api: RatesApiService,
    private val apiKey: String
) : ApiClient {

    override suspend fun getExchangeRates(
        baseCurrency: String,
        targetCurrencies: List<String>
    ): ExchangeRates =
        api.getLatestRates(apiKey, baseCurrency, targetCurrencies.joinToString(","))
}
