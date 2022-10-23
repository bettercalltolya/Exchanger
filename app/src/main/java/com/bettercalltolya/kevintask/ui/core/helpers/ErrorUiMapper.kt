package com.bettercalltolya.kevintask.ui.core.helpers

import android.content.Context
import com.bettercalltolya.common.core.InsufficientBalanceException
import com.bettercalltolya.common.core.NetworkException
import com.bettercalltolya.common.core.NoPendingExchangeException
import com.bettercalltolya.common.core.NoRatesAvailableException
import com.bettercalltolya.kevintask.R

object ErrorUiMapper {

    fun map(e: Throwable, context: Context): String = when (e) {
        is NoPendingExchangeException -> R.string.exchange_error_no_pending_exchange
        is InsufficientBalanceException -> R.string.exchange_error_insufficient_balance
        is NoRatesAvailableException -> R.string.exchange_form_error_no_rates_available
        is NetworkException -> R.string.general_error_no_network
        else -> R.string.general_error_message
    }.let { context.getString(it) }
}
