package com.bettercalltolya.kevintask.ui.history.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bettercalltolya.domain.history.ExchangeHistoryRepository
import com.bettercalltolya.domain.model.ExchangeHistoryItem

class ExchangeHistoryPagingSource(
    private val historyRepository: ExchangeHistoryRepository
): PagingSource<Int, ExchangeHistoryItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ExchangeHistoryItem> {
        val page = params.key ?: 0

        return try {
            val entities = historyRepository.getPagedHistory(
                params.loadSize,
                page * params.loadSize
            )

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ExchangeHistoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
