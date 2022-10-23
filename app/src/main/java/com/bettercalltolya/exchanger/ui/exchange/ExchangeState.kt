package com.bettercalltolya.exchanger.ui.exchange

import com.bettercalltolya.domain.model.PendingExchange

data class ExchangeState(
    val pendingExchange: PendingExchange? = null,
    val loading: Boolean = false,
    val error: Throwable? = null
)
