package com.br.apimercadolivre.searchproducts.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.br.apimercadolivre.general.models.BridgeViewViewModelState
import com.br.apimercadolivre.general.ui.BaseViewModel
import com.br.apimercadolivre.searchproducts.repositories.MeliSite
import com.br.apimercadolivre.searchproducts.repositories.ProdutoMercadoLivreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel : BaseViewModel() {

    private val mState = MutableLiveData<BridgeViewViewModelState>()

    val state: LiveData<BridgeViewViewModelState> = mState

    private val repository: ProdutoMercadoLivreRepository by lazy {
        ProdutoMercadoLivreRepository(
            MeliSite.MLA
        )
    }

    fun searchProductsByName(name: String) = launch(Dispatchers.IO) {
        val result = repository.searchProductsByName(name)
        withContext(Dispatchers.Main) {
            mState.value = if (result.isSuccessful) {
                BridgeViewViewModelState.OnSuccess(result.body())
            } else {
                BridgeViewViewModelState.OnError(Throwable(result.message()))
            }
        }
    }
}