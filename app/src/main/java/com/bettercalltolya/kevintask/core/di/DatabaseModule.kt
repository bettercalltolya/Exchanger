package com.bettercalltolya.kevintask.core.di

import android.content.Context
import androidx.room.Room
import com.bettercalltolya.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideBalanceDao(database: AppDatabase) = database.balanceDao()

    companion object {
        private const val DATABASE_NAME = "kevin_task_database"
    }
}
