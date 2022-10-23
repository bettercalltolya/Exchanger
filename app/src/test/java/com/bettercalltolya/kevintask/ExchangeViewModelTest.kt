package com.bettercalltolya.kevintask

import com.bettercalltolya.common.core.CoroutineDispatchers
import com.bettercalltolya.common.core.NoPendingExchangeException
import com.bettercalltolya.common.core.NoRatesAvailableException
import com.bettercalltolya.common.core.Result
import com.bettercalltolya.domain.currency.CurrencyRepository
import com.bettercalltolya.domain.currency.LastUsedCurrencyRepository
import com.bettercalltolya.domain.model.ExchangeRates
import com.bettercalltolya.domain.model.PendingExchange
import com.bettercalltolya.domain.usecases.ExecuteExchangeUseCase
import com.bettercalltolya.domain.usecases.GetExchangeRatesUseCase
import com.bettercalltolya.domain.usecases.ObserveBalancesUseCase
import com.bettercalltolya.kevintask.ui.exchange.ExchangeState
import com.bettercalltolya.kevintask.ui.exchange.ExchangeViewModel
import com.bettercalltolya.testcommon.BaseTest
import com.bettercalltolya.testcommon.TestCoroutineDispatchers
import com.bettercalltolya.testcommon.test
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExchangeViewModelTest: BaseTest() {

    private lateinit var executeExchangeUseCase: ExecuteExchangeUseCase
    private lateinit var getExchangeRatesUseCase: GetExchangeRatesUseCase
    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var lastUsedCurrencyRepository: LastUsedCurrencyRepository
    private lateinit var observeBalancesUseCase: ObserveBalancesUseCase
    private val dispatchers: CoroutineDispatchers = TestCoroutineDispatchers

    @Before
    fun setUp() {
        executeExchangeUseCase = mock()
        getExchangeRatesUseCase = mock()
        currencyRepository = mock()
        lastUsedCurrencyRepository = mock()
        observeBalancesUseCase = mock()

        whenever(observeBalancesUseCase.invoke()).thenReturn(flowOf(emptyList()))
        whenever(currencyRepository.getAvailableCurrencies()).thenReturn(setOf("EUR", "USD"))
        whenever(lastUsedCurrencyRepository.getLastSoldCurrency()).thenReturn("EUR")
        whenever(lastUsedCurrencyRepository.getLastBoughtCurrency()).thenReturn("USD")
    }

    @Test
    fun `Calculate pending exchange`() = runTest {
        val rates = ExchangeRates(
            "EUR",
            "2022-10-22",
            12345,
            mapOf("USD" to 1.1),
            true
        )
        whenever(getExchangeRatesUseCase(any())).thenReturn(rates)

        val subject = subject()
        val exchangeState = subject.exchangeState.test(this)

        subject.setCurrencyFrom("EUR")
        subject.setCurrencyTo("USD")
        subject.setSellAmount(10.0)

        val stateWithPendingExchange = ExchangeState(
            PendingExchange(
                10.0, "EUR", 11.0,
                "USD", null
            ), false, null
        )
        val values = exchangeState.values()

        assert(values.contains(stateWithPendingExchange))
        exchangeState.finish()
    }

    @Test
    fun `Test NoRatesAvailableException state`() = runTest {
        val rates = ExchangeRates(
            "EUR",
            "2022-10-22",
            12345,
            mapOf(),
            true
        )
        whenever(getExchangeRatesUseCase(any())).thenReturn(rates)

        val subject = subject()
        val exchangeState = subject.exchangeState.test(this)

        subject.setCurrencyFrom("EUR")
        subject.setCurrencyTo("USD")
        subject.setSellAmount(10.0)


        val values = exchangeState.values().last()

        assert(values.error is NoRatesAvailableException)
        exchangeState.finish()
    }

    @Test
    fun `Test error if submitting without pending exchange`() = runTest {
        val subject = subject()

        val exchangeNotifier = subject.exchangeNotifier.test(this)

        subject.executePendingExchange()

        val values = exchangeNotifier.values()
        Assert.assertEquals(1, values.size)
        val value = values.first()
        assert((value as Result.Error).throwable is NoPendingExchangeException)

        exchangeNotifier.finish()
    }

    private fun subject() = ExchangeViewModel(
        dispatchers,
        executeExchangeUseCase,
        getExchangeRatesUseCase,
        currencyRepository,
        lastUsedCurrencyRepository,
        observeBalancesUseCase
    )
}
