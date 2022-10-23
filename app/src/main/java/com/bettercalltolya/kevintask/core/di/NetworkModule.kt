package com.bettercalltolya.kevintask.core.di

import com.bettercalltolya.data.api.RatesApiService
import com.bettercalltolya.data.api.interceptor.ApiResponseErrorInterceptor
import com.bettercalltolya.data.network.NetworkApiClient
import com.bettercalltolya.domain.network.ApiClient
import com.bettercalltolya.kevintask.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideApiClient(
        api: RatesApiService,
        @ApiKey apiKey: String
    ): ApiClient = NetworkApiClient(api, apiKey)

    @Provides
    @Singleton
    fun provideApiService(
        okHttp: OkHttpClient
    ): RatesApiService = with(Retrofit.Builder()) {
        client(okHttp)
        baseUrl(BuildConfig.API_LAYER_BASE_URL)
        addConverterFactory(
            Json.asConverterFactory(MediaType.parse("application/json")!!)
        )
        build()
    }.create(RatesApiService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        with(OkHttpClient.Builder()) {
            val timeout = Duration.ofSeconds(30)
            val log = HttpLoggingInterceptor()
            log.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE

            addInterceptor(log)
            addInterceptor(ApiResponseErrorInterceptor())
            connectTimeout(timeout)
            readTimeout(timeout)
            writeTimeout(timeout)
            pingInterval(timeout)
            build()
        }
}
