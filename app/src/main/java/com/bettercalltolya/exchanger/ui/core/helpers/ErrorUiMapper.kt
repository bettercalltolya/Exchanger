package com.bettercalltolya.exchanger.ui.core.helpers

import android.content.Context
import com.bettercalltolya.common.core.*
import com.bettercalltolya.exchanger.R

object ErrorUiMapper {

    fun map(e: Throwable, context: Context): String = when (e) {
        is NoPendingExchangeException -> R.string.exchange_error_no_pending_exchange
        is InsufficientBalanceException -> R.string.exchange_error_insufficient_balance
        is NoRatesAvailableException -> R.string.exchange_form_error_no_rates_available
        is BuyAmountTooLowException -> R.string.exchange_error_buy_amount_too_low
        is ApiException.ApiKeyLimitExceeded -> R.string.general_error_api_key_limit_exceeded
        is ApiException.Unauthorized -> R.string.general_error_api_unauthorized
        is ApiException.Network -> R.string.general_error_no_network
        else -> R.string.general_error_message
    }.let { context.getString(it) }
}
