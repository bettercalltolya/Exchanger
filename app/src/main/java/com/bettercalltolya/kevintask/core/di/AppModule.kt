package com.bettercalltolya.kevintask.core.di

import com.bettercalltolya.common.core.CurrentTime
import com.bettercalltolya.kevintask.ui.core.dispatchers.CoroutineDispatchers
import com.bettercalltolya.kevintask.ui.core.dispatchers.DefaultCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDispatchers(): CoroutineDispatchers = DefaultCoroutineDispatchers

    @Provides
    @Singleton
    fun provideCurrentTime(): CurrentTime = CurrentTime

    @Provides
    @HistoryPageSize
    fun provideHistoryPageSize(): Int = 15
}
