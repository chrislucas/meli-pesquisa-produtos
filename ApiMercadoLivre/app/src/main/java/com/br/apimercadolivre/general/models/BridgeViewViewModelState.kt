package com.br.apimercadolivre.general.models

sealed class BridgeViewViewModelState {
    data class OnError(val throwable: Throwable) : BridgeViewViewModelState()
    data class OnSuccess<T>(val value: T) : BridgeViewViewModelState()
}