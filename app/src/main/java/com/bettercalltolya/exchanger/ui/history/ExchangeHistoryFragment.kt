package com.bettercalltolya.exchanger.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bettercalltolya.exchanger.databinding.FragmentExchangeHistoryBinding
import com.bettercalltolya.exchanger.ui.core.extensions.fadeIn
import com.bettercalltolya.exchanger.ui.core.extensions.fadeOut
import com.bettercalltolya.exchanger.ui.core.extensions.repeatOnStart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ExchangeHistoryFragment : Fragment() {

    private val vm by viewModels<ExchangeHistoryViewModel>()

    private val adapter = ExchangeHistoryAdapter()

    private lateinit var binding: FragmentExchangeHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentExchangeHistoryBinding
        .inflate(inflater, container, false)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.historyList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        repeatOnStart {
            vm.history
                .onEach(adapter::submitData)
                .launchIn(this)

            vm.isEmpty
                .onEach(::handleIsEmpty)
                .launchIn(this)
        }
    }

    private fun handleIsEmpty(empty: Boolean) {
        if (empty) binding.emptyTitle.fadeIn()
        else binding.emptyTitle.fadeOut()
    }
}
