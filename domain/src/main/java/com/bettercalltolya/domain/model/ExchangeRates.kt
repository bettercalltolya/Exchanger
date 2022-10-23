package com.bettercalltolya.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRates(
    val base: String,
    val date: String,
    val timestamp: Long,
    val rates: Map<String, Double>,
    val success: Boolean
)
