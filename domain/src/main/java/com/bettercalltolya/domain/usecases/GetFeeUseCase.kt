package com.bettercalltolya.domain.usecases

import com.bettercalltolya.domain.history.ExchangeHistoryRepository
import com.bettercalltolya.domain.model.Fee
import javax.inject.Inject

class GetFeeUseCase @Inject constructor(
    private val historyRepository: ExchangeHistoryRepository
) {

    suspend operator fun invoke(
        amount: Double,
        currency: String,
        eurRate: Double?
    ): Fee {
        if (historyRepository.getCount() < FEELESS_COUNT) {
            return Fee(0.0, currency)
        }

        val conversionsCountToday = historyRepository.conversionsCountToday()
        val fee =
            if (conversionsCountToday < ADVANCED_FEE_AFTER_COUNT) amount * BASE_FEE
            else calculateExtendedFee(amount, currency, eurRate)

        return Fee(fee, currency)
    }

    private fun calculateExtendedFee(amount: Double, currency: String, eurRate: Double?): Double {
        val additionalFeeRate = eurRate
            ?.takeIf { currency != CURRENCY_EUR }
            ?: 1.0

        return amount * ADVANCED_FEE + ADVANCED_FEE_EUR * additionalFeeRate
    }

    companion object {
        private const val FEELESS_COUNT = 5
        private const val ADVANCED_FEE_AFTER_COUNT = 15

        private const val BASE_FEE = 0.7 / 100 // 0.7%
        private const val ADVANCED_FEE = 1.2 / 100 // 1.2%

        private const val CURRENCY_EUR = "EUR"
        private const val ADVANCED_FEE_EUR = 0.30
    }
}