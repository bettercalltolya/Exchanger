package com.bettercalltolya.domain.usecases

import com.bettercalltolya.domain.currency.CurrencyRepository
import com.bettercalltolya.domain.model.ExchangeRates
import com.bettercalltolya.domain.network.ApiClient
import com.bettercalltolya.testcommon.BaseTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test

class GetExchangeRatesUseCaseTest : BaseTest() {

    private lateinit var subject: GetExchangeRatesUseCase
    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var apiClient: ApiClient

    @Before
    fun setUp() {
        currencyRepository = mock()
        apiClient = mock()

        whenever(currencyRepository.getAvailableCurrencies()).thenReturn(setOf("EUR", "USD"))

        subject = GetExchangeRatesUseCase(
            currencyRepository,
            apiClient
        )
    }

    @Test
    fun `Filter out base currency from available ones for API request`() = runTest {
        val ratesResponse = ExchangeRates(
            base = "EUR",
            date = "2022-10-22",
            timestamp = 123,
            rates = mapOf("USD" to 0.98),
            success = true
        )
        whenever(apiClient.getExchangeRates(any(), any())).thenReturn(ratesResponse)

        subject.invoke("EUR")

        verify(apiClient).getExchangeRates("EUR", listOf("USD"))
    }
}
