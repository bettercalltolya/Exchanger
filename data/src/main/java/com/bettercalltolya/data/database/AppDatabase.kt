package com.bettercalltolya.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bettercalltolya.data.database.dao.BalanceDao
import com.bettercalltolya.data.database.entities.BalanceEntity

@Database(
    entities = [BalanceEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun balanceDao(): BalanceDao
}
