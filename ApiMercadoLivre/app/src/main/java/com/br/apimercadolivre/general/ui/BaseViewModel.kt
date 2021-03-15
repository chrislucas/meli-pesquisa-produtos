package com.br.apimercadolivre.general.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel() : ViewModel(), CoroutineScope {

    private val viewModelSupervisorJob = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + viewModelSupervisorJob

    override fun onCleared() {
        super.onCleared()
        viewModelSupervisorJob.cancelChildren()
    }
}