package com.bettercalltolya.domain.usecases

import com.bettercalltolya.common.core.NetworkException
import com.bettercalltolya.common.core.Result
import com.bettercalltolya.domain.currency.CurrencyRepository
import com.bettercalltolya.domain.model.ExchangeRates
import com.bettercalltolya.domain.network.ApiClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val apiClient: ApiClient
) {

    operator fun invoke(baseCurrency: String): Flow<Result<ExchangeRates>> = flow {
        emit(Result.loading())

        val currenciesForRates = currencyRepository.getAvailableCurrencies()
            .filterNot { it == baseCurrency }

        while (true) {
            try {
                val rates = apiClient.getExchangeRates(baseCurrency, currenciesForRates)
                emit(Result.of(rates))
            } catch (e: Throwable) {
                if (e is SocketTimeoutException || e is UnknownHostException) {
                    emit(Result.error(NetworkException()))
                } else {
                    emit(Result.error(e))
                }
            }

            delay(RATES_UPDATE_TIMEOUT_MILLIS)
        }

    }

    companion object {
        private val RATES_UPDATE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(5)
    }
}