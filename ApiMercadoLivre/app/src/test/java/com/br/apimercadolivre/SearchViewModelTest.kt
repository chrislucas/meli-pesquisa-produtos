package com.br.apimercadolivre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule


class SearchViewModelTest {

    @Rule
    val archRule = InstantTaskExecutorRule()

    @Rule
    val rule = instantLiveDataAndCoroutineRule
}