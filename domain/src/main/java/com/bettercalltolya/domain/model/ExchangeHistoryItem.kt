package com.bettercalltolya.domain.model

data class ExchangeHistoryItem(
    val sellCurrency: String,
    val sellAmount: Double,
    val buyCurrency: String,
    val buyAmount: Double,
    val feesCurrency: String,
    val feesAmount: Double,
    val timestamp: Long,
    val id: Int = 0
)
