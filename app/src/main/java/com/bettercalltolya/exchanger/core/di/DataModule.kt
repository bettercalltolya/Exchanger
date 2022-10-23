package com.bettercalltolya.exchanger.core.di

import android.content.Context
import com.bettercalltolya.data.balance.DatabaseBalanceRepository
import com.bettercalltolya.data.currency.MockedCurrencyRepository
import com.bettercalltolya.data.currency.PreferencesLastUsedCurrencyRepository
import com.bettercalltolya.data.database.dao.BalanceDao
import com.bettercalltolya.data.database.dao.ExchangeHistoryDao
import com.bettercalltolya.data.history.DatabaseExchangeHistoryRepository
import com.bettercalltolya.domain.balance.BalanceRepository
import com.bettercalltolya.domain.currency.CurrencyRepository
import com.bettercalltolya.domain.currency.LastUsedCurrencyRepository
import com.bettercalltolya.domain.history.ExchangeHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideCurrencyRepository(): CurrencyRepository = MockedCurrencyRepository()

    @Provides
    fun provideBalanceRepository(balanceDao: BalanceDao): BalanceRepository =
        DatabaseBalanceRepository(balanceDao)

    @Provides
    fun provideLastUsedCurrencyRepository(
        @ApplicationContext context: Context
    ): LastUsedCurrencyRepository = PreferencesLastUsedCurrencyRepository(
        context.getSharedPreferences(
            PreferencesLastUsedCurrencyRepository.PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    )

    @Provides
    fun provideExchangeHistoryRepository(
        historyDao: ExchangeHistoryDao
    ): ExchangeHistoryRepository = DatabaseExchangeHistoryRepository(historyDao)
}
