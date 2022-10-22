package com.bettercalltolya.kevintask.core.di

import com.bettercalltolya.data.balance.DatabaseBalanceRepository
import com.bettercalltolya.data.currency.MockedCurrencyRepository
import com.bettercalltolya.data.database.dao.BalanceDao
import com.bettercalltolya.domain.balance.BalanceRepository
import com.bettercalltolya.domain.currency.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideCurrencyRepository(): CurrencyRepository = MockedCurrencyRepository()

    @Provides
    fun provideBalanceRepository(balanceDao: BalanceDao): BalanceRepository =
        DatabaseBalanceRepository(balanceDao)
}
