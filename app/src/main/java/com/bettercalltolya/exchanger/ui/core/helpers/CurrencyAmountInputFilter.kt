package com.bettercalltolya.exchanger.ui.core.helpers

import android.text.InputFilter
import android.text.Spanned

class CurrencyAmountInputFilter : InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence {
        val decimalCount = dest.split('.')
            .takeIf { it.size > 1 }
            ?.get(1)
            ?.length
            ?: 0

        return if (decimalCount < 2) source else ""
    }
}
