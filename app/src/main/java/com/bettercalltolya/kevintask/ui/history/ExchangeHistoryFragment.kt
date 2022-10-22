package com.bettercalltolya.kevintask.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bettercalltolya.kevintask.databinding.FragmentExchangeHistoryBinding

class ExchangeHistoryFragment : Fragment() {

    private lateinit var binding: FragmentExchangeHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentExchangeHistoryBinding
        .inflate(inflater, container, false)
        .also { binding = it }
        .root
}
