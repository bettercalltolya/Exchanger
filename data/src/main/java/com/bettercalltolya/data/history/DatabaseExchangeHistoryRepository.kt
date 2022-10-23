package com.bettercalltolya.data.history

import com.bettercalltolya.data.database.dao.ExchangeHistoryDao
import com.bettercalltolya.data.database.entities.toEntity
import com.bettercalltolya.data.database.entities.toModel
import com.bettercalltolya.domain.history.ExchangeHistoryRepository
import com.bettercalltolya.domain.model.ExchangeHistoryItem

class DatabaseExchangeHistoryRepository(
    private val historyDao: ExchangeHistoryDao
) : ExchangeHistoryRepository {

    override suspend fun getHistory(): List<ExchangeHistoryItem> =
        historyDao.getHistory().map { it.toModel() }

    override suspend fun getPagedHistory(limit: Int, offset: Int): List<ExchangeHistoryItem> =
        historyDao.getPagedHistory(limit, offset).map { it.toModel() }

    override suspend fun isHistoryEmpty(): Boolean =
        historyDao.isHistoryEmpty()

    override suspend fun conversionsCountToday(): Int =
        historyDao.getConversionsCountToday()

    override suspend fun getCount(): Int =
        historyDao.getCount()

    override fun insert(history: ExchangeHistoryItem) {
        historyDao.insert(history.toEntity())
    }
}
