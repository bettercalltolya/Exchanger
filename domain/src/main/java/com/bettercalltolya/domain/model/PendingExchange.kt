package com.bettercalltolya.domain.model

data class PendingExchange(
    val sellAmount: Double,
    val sellCurrency: String,
    val buyAmount: Double,
    val buyCurrency: String
)
