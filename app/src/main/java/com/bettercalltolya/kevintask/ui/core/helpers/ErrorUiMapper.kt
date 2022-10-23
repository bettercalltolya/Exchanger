package com.bettercalltolya.kevintask.ui.core.helpers

import android.content.Context
import com.bettercalltolya.common.core.*
import com.bettercalltolya.kevintask.R

object ErrorUiMapper {

    fun map(e: Throwable, context: Context): String = when (e) {
        is NoPendingExchangeException -> R.string.exchange_error_no_pending_exchange
        is InsufficientBalanceException -> R.string.exchange_error_insufficient_balance
        is NoRatesAvailableException -> R.string.exchange_form_error_no_rates_available
        is BuyAmountTooLowException -> R.string.exchange_error_buy_amount_too_low
        is NetworkException -> R.string.general_error_no_network
        else -> R.string.general_error_message
    }.let { context.getString(it) }
}
