package com.bettercalltolya.domain.usecases

import com.bettercalltolya.common.core.ApiException
import com.bettercalltolya.common.core.CoroutineDispatchers
import com.bettercalltolya.common.core.Result
import com.bettercalltolya.domain.currency.CurrencyRepository
import com.bettercalltolya.domain.model.ExchangeRates
import com.bettercalltolya.domain.network.ApiClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val apiClient: ApiClient,
    private val dispatchers: CoroutineDispatchers
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
                emit(Result.error(e))
                if (e is ApiException.ApiKeyLimitExceeded) break
            }

            delay(RATES_UPDATE_TIMEOUT_MILLIS)
        }
    }.flowOn(dispatchers.io)

    companion object {
        private val RATES_UPDATE_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(1)
    }
}