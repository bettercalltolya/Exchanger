package com.bettercalltolya.domain.usecases

import com.bettercalltolya.domain.balance.BalanceRepository
import com.bettercalltolya.domain.currency.CurrencyRepository
import com.bettercalltolya.domain.model.Balance
import com.bettercalltolya.testcommon.BaseTest
import com.bettercalltolya.testcommon.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class ObserveBalancesUseCaseTest: BaseTest() {

    private lateinit var subject: ObserveBalancesUseCase
    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var balanceRepository: BalanceRepository

    @Before
    fun setUp() {
        currencyRepository = mock()
        balanceRepository = mock()

        whenever(currencyRepository.getAvailableCurrencies()).thenReturn(setOf("EUR", "USD"))
        subject = ObserveBalancesUseCase(
            currencyRepository,
            balanceRepository
        )
    }

    @Test
    fun `Add empty balance for currency user don't have yet`() = runTest {
        val balances = listOf(Balance("EUR", 10.5))
        whenever(balanceRepository.getBalancesFlow()).thenReturn(flowOf(balances))

        val expectedResultWithEmptyBalances = listOf(
            Balance("EUR", 10.5),
            Balance("USD", 0.0)
        )

        subject.invoke().test(this)
            .assertValues(expectedResultWithEmptyBalances)
            .finish()
    }
}
