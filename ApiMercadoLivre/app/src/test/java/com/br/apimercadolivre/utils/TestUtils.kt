package com.br.apimercadolivre.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.RuleChain
import org.junit.rules.TestWatcher
import org.junit.runner.Description


class InstantCoroutineDispatcherRule : TestWatcher() {

    @ExperimentalCoroutinesApi
    override fun starting(description: Description?) {
        super.starting(description)
    }

    @ExperimentalCoroutinesApi
    override fun finished(description: Description?) {
        super.finished(description)
    }
}

val instantLiveDataAndCoroutineRule: RuleChain
    get() = RuleChain.outerRule(InstantCoroutineDispatcherRule())
        .around(InstantCoroutineDispatcherRule())