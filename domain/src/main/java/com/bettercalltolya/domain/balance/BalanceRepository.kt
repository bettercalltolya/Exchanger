package com.bettercalltolya.domain.balance

import com.bettercalltolya.domain.model.Balance
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    suspend fun getBalances(): List<Balance>
    fun getBalancesFlow(): Flow<List<Balance>>
    fun insert(balance: Balance)
    fun insert(balances: List<Balance>)
}
