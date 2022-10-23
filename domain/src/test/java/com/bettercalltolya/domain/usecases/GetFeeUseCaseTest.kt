package com.bettercalltolya.domain.usecases

import com.bettercalltolya.domain.history.ExchangeHistoryRepository
import com.bettercalltolya.domain.model.Fee
import com.bettercalltolya.testcommon.BaseTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetFeeUseCaseTest : BaseTest() {

    private lateinit var subject: GetFeeUseCase
    private lateinit var historyRepository: ExchangeHistoryRepository

    @Before
    fun setUp() {
        historyRepository = mock()

        subject = GetFeeUseCase(historyRepository)
    }

    @Test
    fun `Don't apply fees if user has up to 5 conversions`() = runTest {
        whenever(historyRepository.getCount()).thenReturn(2)

        val expected = Fee(0.0, "EUR")
        val result = subject.invoke(10.0, "EUR", null)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Apply basic fees if user has up to 15 conversions today`() = runTest {
        whenever(historyRepository.getCount()).thenReturn(6)
        whenever(historyRepository.conversionsCountToday()).thenReturn(6)

        val expected = Fee(0.7, "EUR")
        val result = subject.invoke(100.0, "EUR", null)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Apply advanced fees if user has more than 15 conversions today`() = runTest {
        whenever(historyRepository.getCount()).thenReturn(20)
        whenever(historyRepository.conversionsCountToday()).thenReturn(16)

        // 1.2% from 100 + 0.30 EUR
        val expected = Fee(1.5, "EUR")
        val result = subject.invoke(100.0, "EUR", null)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Convert ADVANCED_FEE_EUR to used currency`() = runTest {
        whenever(historyRepository.getCount()).thenReturn(20)
        whenever(historyRepository.conversionsCountToday()).thenReturn(16)

        // 1.2% from 100 + 0.30 EUR * 1.1 EURUSD rate
        val expected = Fee(1.53, "USD")
        val result = subject.invoke(100.0, "USD", 1.1)

        Assert.assertEquals(expected, result)
    }
}
