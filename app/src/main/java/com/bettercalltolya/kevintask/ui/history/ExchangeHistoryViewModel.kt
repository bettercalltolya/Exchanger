package com.bettercalltolya.kevintask.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bettercalltolya.domain.history.ExchangeHistoryRepository
import com.bettercalltolya.kevintask.core.di.HistoryPageSize
import com.bettercalltolya.kevintask.ui.history.paging.ExchangeHistoryPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class ExchangeHistoryViewModel @Inject constructor(
    historyRepository: ExchangeHistoryRepository,
    @HistoryPageSize pageSize: Int
) : ViewModel() {

    val isEmpty = flow { emit(historyRepository.isHistoryEmpty()) }
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    val history = Pager(
        PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false,
            initialLoadSize = pageSize
        ),
    ) { ExchangeHistoryPagingSource(historyRepository) }
        .flow.cachedIn(viewModelScope)
}
