package com.bettercalltolya.domain.usecases

import com.bettercalltolya.common.core.CurrentTime
import com.bettercalltolya.common.core.InsufficientBalanceException
import com.bettercalltolya.domain.balance.BalanceRepository
import com.bettercalltolya.domain.currency.LastUsedCurrencyRepository
import com.bettercalltolya.domain.history.ExchangeHistoryRepository
import com.bettercalltolya.domain.model.ExchangeHistoryItem
import com.bettercalltolya.common.core.Result
import com.bettercalltolya.domain.model.Balance
import com.bettercalltolya.domain.model.PendingExchange
import javax.inject.Inject

class ExecuteExchangeUseCase @Inject constructor(
    private val historyRepository: ExchangeHistoryRepository,
    private val balanceRepository: BalanceRepository,
    private val lastUsedCurrencyRepository: LastUsedCurrencyRepository,
    private val getFeeUseCase: GetFeeUseCase,
    private val currentTime: CurrentTime
) {
    suspend operator fun invoke(pendingExchange: PendingExchange): Result<ExchangeHistoryItem> {
        val epochSeconds = currentTime.epochSeconds()

        val fee = getFeeUseCase(
            pendingExchange.sellAmount,
            pendingExchange.sellCurrency,
            pendingExchange.eurRate
        )

        val sellAmountWithFees = pendingExchange.sellAmount + fee.feeAmount

        val sellBalance = balanceRepository.getByCurrency(pendingExchange.sellCurrency)
            ?: Balance(pendingExchange.sellCurrency, 0.0)
        val buyBalance = balanceRepository.getByCurrency(pendingExchange.buyCurrency)
            ?: Balance(pendingExchange.buyCurrency, 0.0)

        if (sellBalance.amount < sellAmountWithFees) {
            return Result.error(InsufficientBalanceException())
        }

        val newSellBalance = sellBalance.copy(amount = sellBalance.amount - sellAmountWithFees)
        val newBuyBalance = buyBalance.copy(amount = buyBalance.amount + pendingExchange.buyAmount)

        val historyItem = ExchangeHistoryItem(
            pendingExchange.sellCurrency,
            pendingExchange.sellAmount,
            pendingExchange.buyCurrency,
            pendingExchange.buyAmount,
            fee.feeCurrency,
            fee.feeAmount,
            epochSeconds
        )

        historyRepository.insert(historyItem)
        lastUsedCurrencyRepository.setLastSoldCurrency(pendingExchange.sellCurrency)
        lastUsedCurrencyRepository.setLastBoughtCurrency(pendingExchange.buyCurrency)
        balanceRepository.insert(listOf(newSellBalance, newBuyBalance))

        return Result.of(historyItem)
    }
}
