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
import retrofit2.Response

class SearchViewModel : BaseViewModel() {

    private val mState = MutableLiveData<BridgeViewViewModelState>()

    val state: LiveData<BridgeViewViewModelState> = mState

    private val repository: ProdutoMercadoLivreRepository by lazy {
        ProdutoMercadoLivreRepository(
            MeliSite.MLA
        )
    }

    fun searchProductsByName(name: String) = launch {
        repository.searchProductsByName(name).let { response ->
            withContext(Dispatchers.Main) {
                mState.value = if (response.isSuccessful) {
                    BridgeViewViewModelState.OnSuccess(response.body())
                } else {
                    BridgeViewViewModelState.OnError(Throwable(response.message()))
                }
            }
        }
    }
}