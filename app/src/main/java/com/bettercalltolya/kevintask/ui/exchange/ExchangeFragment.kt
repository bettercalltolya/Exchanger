package com.bettercalltolya.kevintask.ui.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bettercalltolya.kevintask.databinding.FragmentExchangeBinding
import com.bettercalltolya.kevintask.ui.core.extensions.repeatOnStart
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

        binding.balancesList.adapter = adapter
        binding.balancesList.itemAnimator = null
    }

    override fun onStart() {
        super.onStart()

        repeatOnStart {
            vm.balances
                .onEach { adapter.submitList(it) }
                .launchIn(this)
        }
    }
}
