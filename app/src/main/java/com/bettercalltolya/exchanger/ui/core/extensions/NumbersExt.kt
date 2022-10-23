package com.bettercalltolya.exchanger.ui.core.extensions

fun Double.toCurrencyString(
    currencyCode: String? = null
): String =
    if (currencyCode.isNullOrBlank()) String.format("%.2f", this)
    else String.format("%.2f %s", this, currencyCode)
