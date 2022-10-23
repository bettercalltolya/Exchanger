package com.bettercalltolya.domain.currency

interface LastUsedCurrencyRepository {

    fun getLastSoldCurrency(default: String = "EUR"): String
    fun setLastSoldCurrency(currency: String)

    fun getLastBoughtCurrency(default: String = "GBP"): String
    fun setLastBoughtCurrency(currency: String)
}
