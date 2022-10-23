package com.bettercalltolya.data.currency

import android.content.SharedPreferences
import com.bettercalltolya.domain.currency.LastUsedCurrencyRepository

class PreferencesLastUsedCurrencyRepository(
    private val preferences: SharedPreferences
) : LastUsedCurrencyRepository {

    override fun getLastSoldCurrency(default: String): String {
        return preferences.getString(KEY_SOLD_CURRENCY, default) ?: default
    }

    override fun setLastSoldCurrency(currency: String) {
        preferences.edit()
            .putString(KEY_SOLD_CURRENCY, currency)
            .apply()
    }

    override fun getLastBoughtCurrency(default: String): String {
        return preferences.getString(KEY_BOUGHT_CURRENCY, default) ?: default
    }

    override fun setLastBoughtCurrency(currency: String) {
        preferences.edit()
            .putString(KEY_BOUGHT_CURRENCY, currency)
            .apply()
    }

    companion object {
        private const val KEY_SOLD_CURRENCY = "key.sold_currency"
        private const val KEY_BOUGHT_CURRENCY = "key.bought_currency"

        const val PREFERENCES_NAME = "preferences.last_used_currency_repository"
    }
}
