package com.bettercalltolya.data.api

import com.bettercalltolya.domain.model.ExchangeRates
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RatesApiService {

    @GET("exchangerates_data/latest")
    suspend fun getLatestRates(
        @Header("apiKey") apiKey: String,
        @Query("base") baseCurrency: String,
        @Query("symbols") targetCurrencies: String
    ): ExchangeRates
}
