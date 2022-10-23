package com.bettercalltolya.exchanger.core.di

import android.content.Context
import com.bettercalltolya.data.database.AppDatabase
import com.bettercalltolya.data.database.DatabaseProvider
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
        DatabaseProvider.getInstance(context)

    @Provides
    @Singleton
    fun provideBalanceDao(database: AppDatabase) = database.balanceDao()

    @Provides
    @Singleton
    fun provideExchangeHistoryDao(database: AppDatabase) = database.exchangeHistoryDao()
}
