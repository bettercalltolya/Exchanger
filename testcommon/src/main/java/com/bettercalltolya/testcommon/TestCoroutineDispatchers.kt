package com.bettercalltolya.testcommon

import com.bettercalltolya.common.core.CoroutineDispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher

object TestCoroutineDispatchers : CoroutineDispatchers {
    override val main = UnconfinedTestDispatcher()
    override val io = UnconfinedTestDispatcher()
    override val default = UnconfinedTestDispatcher()
    override val unconfined = UnconfinedTestDispatcher()
}