package com.bettercalltolya.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bettercalltolya.data.database.entities.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    // Dirty way to create initial balance on first launch
    @Transaction
    suspend fun checkAndInsertInitialBalance() {
        val balances = getBalances()

        if (balances.isEmpty() || balances.all { it.amount == 0.0 }) {
            insert(BalanceEntity("EUR", 1000.0))
        }
    }

    @Query("SELECT * FROM BalanceEntity")
    fun getBalancesFlow(): Flow<List<BalanceEntity>>

    @Query("SELECT * FROM BalanceEntity")
    suspend fun getBalances(): List<BalanceEntity>

    @Query("SELECT * FROM BalanceEntity WHERE currency = :currency")
    suspend fun getByCurrency(currency: String): BalanceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(balance: BalanceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(balances: List<BalanceEntity>)
}
