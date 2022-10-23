package com.bettercalltolya.data.network

import com.bettercalltolya.domain.model.ExchangeRates
import com.bettercalltolya.domain.network.ApiClient
import kotlinx.coroutines.delay
import kotlin.random.Random

class NetworkApiClient : ApiClient {

    private val rand = Random(1)

    override suspend fun getExchangeRates(
        baseCurrency: String,
        targetCurrencies: List<String>
    ): ExchangeRates {
        delay(1000)
        return ExchangeRates(
            "EUR",
            "2022-10-22",
            12345,
            mapOf(
                "GBP" to rand.nextDouble(0.8, 1.0),
                "USD" to rand.nextDouble(1.0, 1.2),
                "JPY" to rand.nextDouble(95.0, 109.0)
            ),
            true
        )
    }
}
