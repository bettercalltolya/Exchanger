package com.bettercalltolya.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bettercalltolya.domain.model.Balance

@Entity
data class BalanceEntity(
    @PrimaryKey
    val currency: String,
    val amount: Double
)

fun BalanceEntity.toModel() = Balance(currency, amount)
fun Balance.toEntity() = BalanceEntity(currency, amount)
