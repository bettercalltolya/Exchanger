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

            emit(balances.mapWithEmptyBalances(currencies))
        }

    private fun List<Balance>.mapWithEmptyBalances(currencies: Set<String>): List<Balance> {
        val balanceCurrencies = map { it.currency }
        val emptyBalances = currencies
            .filterNot { balanceCurrencies.contains(it) }
            .map { Balance(it, 0.0) }

        return (this + emptyBalances).sortedBy { it.currency }
    }
}
