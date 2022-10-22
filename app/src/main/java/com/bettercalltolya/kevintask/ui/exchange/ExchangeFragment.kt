package com.bettercalltolya.kevintask.ui.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bettercalltolya.kevintask.databinding.FragmentExchangeBinding

class ExchangeFragment : Fragment() {

    private lateinit var binding: FragmentExchangeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentExchangeBinding
        .inflate(inflater, container, false)
        .also { binding = it }
        .root
}
