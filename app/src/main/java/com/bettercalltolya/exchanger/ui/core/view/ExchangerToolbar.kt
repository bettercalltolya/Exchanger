package com.bettercalltolya.exchanger.ui.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bettercalltolya.exchanger.databinding.ExchangerToolbarBinding
import com.bettercalltolya.exchanger.ui.core.extensions.fadeIn
import com.bettercalltolya.exchanger.ui.core.extensions.fadeOut
import com.bettercalltolya.exchanger.ui.core.extensions.setDebounceClickListener

class ExchangerToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    private val binding = ExchangerToolbarBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setTitle(text: CharSequence?) {
        binding.title.text = text
    }

    /* Back button section */
    fun showBackButton() {
        binding.backButton.fadeIn()
    }

    fun hideBackButton() {
        binding.backButton.fadeOut()
    }

    fun setOnBackClickListener(listener: () -> Unit) {
        binding.backButton.setDebounceClickListener { listener() }
    }

    /* History button section */
    fun showHistoryButton() {
        binding.historyButton.fadeIn()
    }

    fun hideHistoryButton() {
        binding.historyButton.fadeOut()
    }

    fun setOnHistoryClickListener(listener: () -> Unit) {
        binding.historyButton.setDebounceClickListener { listener() }
    }
}
