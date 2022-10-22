package com.bettercalltolya.domain.currency

interface CurrencyRepository {

    fun getAvailableCurrencies(): Set<String>
}
