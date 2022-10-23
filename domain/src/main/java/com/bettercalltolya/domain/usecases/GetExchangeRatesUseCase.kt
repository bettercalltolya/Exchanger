package com.bettercalltolya.domain.usecases

import com.bettercalltolya.domain.currency.CurrencyRepository
import com.bettercalltolya.domain.model.ExchangeRates
import com.bettercalltolya.domain.network.ApiClient
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val apiClient: ApiClient,
) {

    suspend operator fun invoke(baseCurrency: String): ExchangeRates {
        val currenciesForRates = currencyRepository.getAvailableCurrencies()
            .filterNot { it == baseCurrency }

        return apiClient.getExchangeRates(baseCurrency, currenciesForRates)
    }
}
