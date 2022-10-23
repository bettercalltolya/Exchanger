package com.bettercalltolya.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bettercalltolya.data.database.dao.BalanceDao
import com.bettercalltolya.data.database.dao.ExchangeHistoryDao
import com.bettercalltolya.data.database.entities.BalanceEntity
import com.bettercalltolya.data.database.entities.ExchangeHistoryEntity

@Database(
    entities = [BalanceEntity::class, ExchangeHistoryEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun balanceDao(): BalanceDao
    abstract fun exchangeHistoryDao(): ExchangeHistoryDao
}
