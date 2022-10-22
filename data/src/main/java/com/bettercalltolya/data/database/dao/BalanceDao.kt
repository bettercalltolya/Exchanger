package com.bettercalltolya.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bettercalltolya.data.database.entities.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    @Query("SELECT * FROM BalanceEntity")
    fun getBalancesFlow(): Flow<List<BalanceEntity>>

    @Query("SELECT * FROM BalanceEntity")
    suspend fun getBalances(): List<BalanceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(balance: BalanceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(balances: List<BalanceEntity>)
}
