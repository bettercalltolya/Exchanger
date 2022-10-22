package com.bettercalltolya.domain.usecases

import com.bettercalltolya.domain.balance.BalanceRepository
import com.bettercalltolya.domain.currency.CurrencyRepository
import com.bettercalltolya.domain.model.Balance
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject

class ObserveBalancesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val balanceRepository: BalanceRepository
) {

    operator fun invoke() = balanceRepository.getBalancesFlow()
        .transformLatest { balances ->
            val currencies = currencyRepository.getAvailableCurrencies()

            if (balances.isEmpty()) {
                emit(currencies.mapToInitialBalance())
                return@transformLatest
            }

            emit(balances.mapWithEmptyBalances(currencies))
        }

    private fun List<Balance>.mapWithEmptyBalances(currencies: Set<String>): List<Balance> {
        val balanceCurrencies = map { it.currency }
        val emptyBalances = currencies
            .filterNot { balanceCurrencies.contains(it) }
            .map { Balance(it, 0.0) }

        return this + emptyBalances
    }

    // Hardcoding initial balance like that to keep things simple
    private fun Set<String>.mapToInitialBalance(): List<Balance> {
        if (isEmpty()) return emptyList()

        val eurOrFirstIndex = indexOf("EUR")
            .takeIf { it >= 0 }
            ?: 0

        return mapIndexed { i, currency ->
            if (i == eurOrFirstIndex) Balance(currency, 1000.0)
            else Balance(currency, 0.0)
        }
    }
}
