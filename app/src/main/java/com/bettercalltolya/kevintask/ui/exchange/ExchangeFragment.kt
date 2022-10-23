package com.bettercalltolya.kevintask.ui.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bettercalltolya.common.core.Result
import com.bettercalltolya.domain.model.ExchangeHistoryItem
import com.bettercalltolya.kevintask.R
import com.bettercalltolya.kevintask.databinding.FragmentExchangeBinding
import com.bettercalltolya.kevintask.ui.core.extensions.*
import com.bettercalltolya.kevintask.ui.core.helpers.CurrencyAmountInputFilter
import com.bettercalltolya.kevintask.ui.core.helpers.ErrorUiMapper
import com.bettercalltolya.kevintask.ui.exchange.balance.BalancesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ExchangeFragment : Fragment() {

    private val vm by viewModels<ExchangeViewModel>()

    private lateinit var binding: FragmentExchangeBinding
    private val adapter = BalancesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentExchangeBinding
        .inflate(inflater, container, false)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            balancesList.adapter = adapter
            balancesList.itemAnimator = null

            currencyFromInputField.filters += CurrencyAmountInputFilter()
            currencyFromInputField.addTextChangedListener {
                val amount = it.toString().toDoubleOrNull() ?: 0.0
                vm.setSellAmount(amount)
            }

            buyButton.setDebounceClickListener { vm.executePendingExchange() }

            currencyFromPicker.setDebounceClickListener {
                openPicker(
                    vm.currencies.value.filterNot { it == vm.currencyTo.value }.toTypedArray(),
                    vm.currencyFrom.value,
                    R.string.exchange_picker_title_sell
                ) {
                    vm.setCurrencyFrom(it)
                }
            }
            currencyToPicker.setDebounceClickListener {
                openPicker(
                    vm.currencies.value.filterNot { it == vm.currencyFrom.value }.toTypedArray(),
                    vm.currencyTo.value,
                    R.string.exchange_picker_title_buy
                ) {
                    vm.setCurrencyTo(it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        repeatOnStart {
            vm.balances
                .onEach(adapter::submitList)
                .launchIn(this)

            vm.currencyFrom
                .onEach { binding.currencyFrom.text = it }
                .launchIn(this)

            vm.currencyTo
                .onEach { binding.currencyTo.text = it }
                .launchIn(this)

            vm.availableBalance
                .onEach { binding.availableBalanceValue.text = it }
                .launchIn(this)

            vm.exchangeState
                .onEach(::handleExchangeState)
                .launchIn(this)

            vm.exchangeNotifier
                .onEach(::handleExchangeResult)
                .launchIn(this)
        }
    }

    private fun handleExchangeState(exchangeState: ExchangeState) {
        with(binding) {
            if (exchangeState.loading) loadingOverlay.fadeIn()
            else loadingOverlay.fadeOut()

            if (exchangeState.error != null) {
                error.text = ErrorUiMapper.map(exchangeState.error, requireContext())
                error.isVisible = true
            } else {
                error.isVisible = false
                error.text = ""
            }

            val amount = exchangeState.pendingExchange?.buyAmount?.toCurrencyString() ?: "0.00"
            currencyToInputField.setText(amount)
        }
    }

    private fun handleExchangeResult(result: Result<ExchangeHistoryItem>) {
        if (result is Result.Error) {
             Toast.makeText(
                 requireContext(),
                 ErrorUiMapper.map(result.throwable, requireContext()),
                 Toast.LENGTH_LONG
             ).show()
        } else if (result is Result.Success) {
            val item = result.value

            val message = if (item.feesAmount > 0.0) {
                getString(
                    R.string.exchange_success_alert_message_with_fees_fmt,
                    item.sellAmount,
                    item.sellCurrency,
                    item.buyAmount,
                    item.buyCurrency,
                    item.feesAmount,
                    item.feesCurrency
                )
            } else {
                getString(
                    R.string.exchange_success_alert_message_fmt,
                    item.sellAmount,
                    item.sellCurrency,
                    item.buyAmount,
                    item.buyCurrency
                )
            }

            AlertDialog.Builder(requireContext())
                .setTitle(R.string.exchange_success_alert_title)
                .setMessage(message)
                .setPositiveButton(R.string.general_ok, null)
                .show()
        }
    }

    private fun openPicker(
        data: Array<String>,
        selected: String,
        @StringRes title: Int,
        onPick: (String) -> Unit
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setSingleChoiceItems(data, data.indexOf(selected)) { d, id ->
                onPick(data[id])
                d.dismiss()
            }
            .setPositiveButton(R.string.general_cancel, null)
            .show()
    }
}
