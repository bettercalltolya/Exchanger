package com.bettercalltolya.domain.history

import com.bettercalltolya.domain.model.ExchangeHistoryItem

interface ExchangeHistoryRepository {
    suspend fun getHistory(): List<ExchangeHistoryItem>
    suspend fun getPagedHistory(limit: Int, offset: Int): List<ExchangeHistoryItem>
    suspend fun isHistoryEmpty(): Boolean
    suspend fun conversionsCountToday(): Int
    suspend fun getCount(): Int
    fun insert(history: ExchangeHistoryItem)
}
