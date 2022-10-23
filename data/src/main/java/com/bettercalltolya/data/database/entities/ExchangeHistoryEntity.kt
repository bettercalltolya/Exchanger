package com.bettercalltolya.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bettercalltolya.domain.model.ExchangeHistoryItem

@Entity
data class ExchangeHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sellCurrency: String,
    val sellAmount: Double,
    val buyCurrency: String,
    val buyAmount: Double,
    val feesCurrency: String,
    val feesAmount: Double,
    val timestamp: Long
)

fun ExchangeHistoryEntity.toModel() = ExchangeHistoryItem(
    sellCurrency,
    sellAmount,
    buyCurrency,
    buyAmount,
    feesCurrency,
    feesAmount,
    timestamp,
    id
)

fun ExchangeHistoryItem.toEntity() = ExchangeHistoryEntity(
    sellCurrency = sellCurrency,
    sellAmount = sellAmount,
    buyCurrency = buyCurrency,
    buyAmount = buyAmount,
    feesCurrency = feesCurrency,
    feesAmount = feesAmount,
    timestamp = timestamp
)
