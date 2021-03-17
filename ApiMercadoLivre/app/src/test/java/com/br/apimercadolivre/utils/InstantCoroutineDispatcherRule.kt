package com.br.apimercadolivre.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.RuleChain
import org.junit.rules.TestWatcher
import org.junit.runner.Description


class InstantCoroutineDispatcherRule(
    // private val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    @ExperimentalCoroutinesApi
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @ExperimentalCoroutinesApi
    override fun finished(description: Description?) {
        super.finished(description)
        //Dispatchers.resetMain()
        //testCoroutineDispatcher.cleanupTestCoroutines()
    }

    companion object {
        val instantLiveDataAndCoroutineRule: RuleChain
            get() = RuleChain.outerRule(InstantCoroutineDispatcherRule())
                .around(InstantTaskExecutorRule())
    }
}
