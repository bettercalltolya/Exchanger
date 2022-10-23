package com.bettercalltolya.testcommon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.test.TestScope
import org.junit.Rule
import org.junit.rules.TestRule

abstract class BaseTest {

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    protected fun runTest(block: suspend TestScope.() -> Unit) = coroutineRule.runTest(block)
}
