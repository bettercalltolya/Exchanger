package com.bettercalltolya.testcommon

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.junit.Assert

fun <T> Flow<T>.test(scope: CoroutineScope, timeOut: Long = 10000): TestObserver<T> {
    return TestObserver(scope, timeOut,this)
}

class TestObserver<T>(
    scope: CoroutineScope,
    timeOut: Long,
    flow: Flow<T>
) {
    private val values = mutableListOf<T>()

    private val job: Job = scope.launch {
        withTimeout(timeOut) {
            flow.collect { values.add(it) }
        }
    }

    fun values() = values.toList()

    fun assertNoValues(): TestObserver<T> {
        Assert.assertEquals(emptyList<T>(), this.values)
        return this
    }

    fun assertValues(vararg values: T): TestObserver<T> {
        Assert.assertEquals(values.toList(), this.values)
        return this
    }

    fun finish() {
        job.cancel()
    }
}
