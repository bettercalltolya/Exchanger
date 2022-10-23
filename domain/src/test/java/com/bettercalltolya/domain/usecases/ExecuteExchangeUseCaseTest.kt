package com.bettercalltolya.domain.usecases

import com.bettercalltolya.common.core.CurrentTime
import com.bettercalltolya.common.core.InsufficientBalanceException
import com.bettercalltolya.common.core.Result
import com.bettercalltolya.domain.balance.BalanceRepository
import com.bettercalltolya.domain.currency.LastUsedCurrencyRepository
import com.bettercalltolya.domain.history.ExchangeHistoryRepository
import com.bettercalltolya.domain.model.Balance
import com.bettercalltolya.domain.model.ExchangeHistoryItem
import com.bettercalltolya.domain.model.Fee
import com.bettercalltolya.domain.model.PendingExchange
import com.bettercalltolya.testcommon.BaseTest
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExecuteExchangeUseCaseTest : BaseTest() {

    private lateinit var subject: ExecuteExchangeUseCase
    private lateinit var historyRepository: ExchangeHistoryRepository
    private lateinit var balanceRepository: BalanceRepository
    private lateinit var lastUsedCurrencyRepository: LastUsedCurrencyRepository
    private lateinit var getFeeUseCase: GetFeeUseCase
    private lateinit var currentTime: CurrentTime

    @Before
    fun setUp() {
        historyRepository = mock()
        balanceRepository = mock()
        lastUsedCurrencyRepository = mock()
        getFeeUseCase = mock()
        currentTime = mock()

        subject = ExecuteExchangeUseCase(
            historyRepository,
            balanceRepository,
            lastUsedCurrencyRepository,
            getFeeUseCase,
            currentTime
        )
    }

    @Test
    fun `Test success path`() = runTest {
        val eurBalance = Balance("EUR", 100.0)
        val usdBalance = Balance("USD", 100.0)
        val fee = Fee(0.0, "EUR")
        whenever(currentTime.epochSeconds()).thenReturn(12345)
        whenever(balanceRepository.getByCurrency("EUR")).thenReturn(eurBalance)
        whenever(balanceRepository.getByCurrency("USD")).thenReturn(usdBalance)
        whenever(getFeeUseCase.invoke(any(), any(), anyOrNull())).thenReturn(fee)

        val pendingExchange = PendingExchange(
            10.0, "EUR", 15.0, "USD", null
        )

        val historyItem = ExchangeHistoryItem(
            "EUR", 10.0, "USD", 15.0,
            "EUR", 0.0, 12345
        )
        val expectedResult = Result.of(historyItem)
        val newEurBalance = Balance("EUR", 90.0)
        val newUsdBalance = Balance("USD", 115.0)

        val result = subject.invoke(pendingExchange)

        verify(currentTime).epochSeconds()
        verify(getFeeUseCase).invoke(10.0, "EUR", null)
        verify(balanceRepository, times(2)).getByCurrency(any())

        verify(historyRepository).insert(historyItem)
        verify(lastUsedCurrencyRepository).setLastSoldCurrency(any())
        verify(lastUsedCurrencyRepository).setLastBoughtCurrency("USD")
        verify(balanceRepository).insert(listOf(newEurBalance, newUsdBalance))

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `Test insufficient balance error`() = runTest {
        val eurBalance = Balance("EUR", 100.0)
        val usdBalance = Balance("USD", 100.0)
        val fee = Fee(11.0, "EUR")
        whenever(currentTime.epochSeconds()).thenReturn(12345)
        whenever(balanceRepository.getByCurrency("EUR")).thenReturn(eurBalance)
        whenever(balanceRepository.getByCurrency("USD")).thenReturn(usdBalance)
        whenever(getFeeUseCase.invoke(any(), any(), anyOrNull())).thenReturn(fee)

        val pendingExchange = PendingExchange(
            90.0, "EUR", 120.0, "USD", null
        )


        val result = subject.invoke(pendingExchange)

        assert(result is Result.Error)
        assert((result as Result.Error).throwable is InsufficientBalanceException)
    }
}
