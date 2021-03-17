package com.br.apimercadolivre.searchproducts.viewmodels.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

object ViewModelProviderUtils {

    @JvmStatic
    fun <V : ViewModel> get(
        viewModelStore: ViewModelStore,
        factoryViewModel: ViewModelProvider.Factory,
        viewModelClass: Class<V>
    ): V {

        return ViewModelProvider(viewModelStore, factoryViewModel).get(viewModelClass)
    }
}