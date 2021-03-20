package com.br.apimercadolivre.searchproducts.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.br.apimercadolivre.general.models.BridgeViewViewModelState
import com.br.apimercadolivre.general.ui.BaseViewModel
import com.br.apimercadolivre.searchproducts.repositories.MeliSite
import com.br.apimercadolivre.searchproducts.repositories.ProdutoMercadoLivreRepository
import com.br.apimercadolivre.searchproducts.repositories.provideMercadoLivreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val mSite: MeliSite) : BaseViewModel() {

    var site: MeliSite = mSite
        get() = mSite
        set(value) {
            field = value
            repository.site = field.site
        }

    private val mState = MutableLiveData<BridgeViewViewModelState>()

    val state: LiveData<BridgeViewViewModelState> = mState

    private val repository: ProdutoMercadoLivreRepository by lazy {
        provideMercadoLivreRepository(
            mSite
        )
    }


    fun searchProductsByName(name: String) = launch {
        repository.searchProductsByName(name).let { response ->
            withContext(Dispatchers.IO) {
                val state = if (response.isSuccessful) {
                    BridgeViewViewModelState.OnSuccess(response.body())
                } else {
                    BridgeViewViewModelState.OnError(Throwable(response.message()))
                }
                mState.postValue(state)
            }
        }
    }
}