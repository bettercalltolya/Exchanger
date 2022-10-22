package com.bettercalltolya.kevintask.ui.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettercalltolya.domain.usecases.ObserveBalancesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    observeBalancesUseCase: ObserveBalancesUseCase
) : ViewModel() {

    val balances = observeBalancesUseCase()
        .distinctUntilChanged()
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)
}
