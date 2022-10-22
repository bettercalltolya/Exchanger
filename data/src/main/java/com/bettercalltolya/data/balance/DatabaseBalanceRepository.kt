package com.bettercalltolya.data.balance

import com.bettercalltolya.data.database.dao.BalanceDao
import com.bettercalltolya.data.database.entities.BalanceEntity
import com.bettercalltolya.data.database.entities.toEntity
import com.bettercalltolya.data.database.entities.toModel
import com.bettercalltolya.domain.balance.BalanceRepository
import com.bettercalltolya.domain.model.Balance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseBalanceRepository(private val dao: BalanceDao) : BalanceRepository {

    override suspend fun getBalances(): List<Balance> =
        dao.getBalances().map(BalanceEntity::toModel)

    override fun getBalancesFlow(): Flow<List<Balance>> =
        dao.getBalancesFlow().map { balances -> balances.map(BalanceEntity::toModel) }

    override fun insert(balance: Balance) = dao.insert(balance.toEntity())

    override fun insert(balances: List<Balance>) = dao.insert(balances.map(Balance::toEntity))
}
