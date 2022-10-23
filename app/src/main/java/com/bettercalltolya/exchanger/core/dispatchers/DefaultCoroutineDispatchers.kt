package com.bettercalltolya.exchanger.core.dispatchers

import com.bettercalltolya.common.core.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers

object DefaultCoroutineDispatchers : CoroutineDispatchers {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}
