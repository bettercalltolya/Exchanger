package com.bettercalltolya.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bettercalltolya.data.database.entities.ExchangeHistoryEntity

@Dao
interface ExchangeHistoryDao {

    @Query("SELECT * FROM ExchangeHistoryEntity ORDER BY timestamp DESC")
    suspend fun getHistory(): List<ExchangeHistoryEntity>

    @Query("SELECT * FROM ExchangeHistoryEntity ORDER BY timestamp DESC LIMIT :limit OFFSET :offset")
    suspend fun getPagedHistory(limit: Int, offset: Int): List<ExchangeHistoryEntity>

    @Query("SELECT NOT EXISTS(SELECT * FROM ExchangeHistoryEntity LIMIT 1)")
    suspend fun isHistoryEmpty(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: ExchangeHistoryEntity)
}
