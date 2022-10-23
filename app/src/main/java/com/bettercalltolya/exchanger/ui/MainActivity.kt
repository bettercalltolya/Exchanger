package com.bettercalltolya.exchanger.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.bettercalltolya.exchanger.R
import com.bettercalltolya.exchanger.databinding.ActivityMainBinding
import com.bettercalltolya.exchanger.ui.core.view.ExchangerToolbar
import com.bettercalltolya.exchanger.ui.exchange.ExchangeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setupListeners()

        navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        with(binding.toolbar) {
            setTitle(destination.label)

            if (destination.id == R.id.exchangeFragment) {
                showHistoryButton()
                hideBackButton()
            } else {
                hideHistoryButton()
                showBackButton()
            }
        }
    }

    private fun ExchangerToolbar.setupListeners() {
        setOnHistoryClickListener {
            navController.navigate(ExchangeFragmentDirections.toExchangeHistory())
        }
        setOnBackClickListener {
            navController.navigateUp()
        }
    }
}
