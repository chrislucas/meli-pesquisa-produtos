package com.br.apimercadolivre.searchproducts.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.br.apimercadolivre.general.models.BridgeViewViewModelState
import com.br.apimercadolivre.searchproducts.models.endpoint.MercadoLivreEndpoint
import com.br.apimercadolivre.searchproducts.models.models.ResultSearchProduct
import com.br.apimercadolivre.searchproducts.repositories.ProdutoMercadoLivreRepository
import com.br.apimercadolivre.utils.fromJsonToObject
import com.br.apimercadolivre.utils.instantLiveDataAndCoroutineRule
import com.br.apimercadolivre.utils.provideGsonInstance
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.lang.Exception
import kotlin.test.assertEquals


class SearchViewModelTest {

    @get:Rule
    val archRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = instantLiveDataAndCoroutineRule


    private lateinit var repository: ProdutoMercadoLivreRepository
    private lateinit var viewModel: SearchViewModel

    private lateinit var result: ResultSearchProduct

    private lateinit var endpoint: MercadoLivreEndpoint

    @Before
    fun setup() {
        repository = mockk()
        endpoint = mockk()
        viewModel = SearchViewModel()
    }


    @Test
    fun `ao realizar uma requisicao, ela devolvendo uma lista de produtos estado da viewmodel deve sucesso`() {
        result = provideGsonInstance().fromJsonToObject("search_product/result_search_product.json")

        coEvery {
            repository.searchProductsByName(any())
        } returns Response.success(result)


        coEvery {
            endpoint.searchProductsByName(any())
        } returns Response.success(result)


        runBlocking {
            viewModel.searchProductsByName("corda de pular")
            assertEquals(BridgeViewViewModelState.OnSuccess(result), viewModel.state.value)
        }
    }


    @Test
    fun `ao realizar uma requisicao, ela devolvendo lista de produtos estado da viewmodel deve sucesso`() {
        result = provideGsonInstance().fromJsonToObject("search_product/result_search_product.json")

        coEvery {
            repository.searchProductsByName(any())
        } returns Response.error(
            500,
            ResponseBody.create(MediaType.parse("application/json"), "{}")
        )


        runBlocking {
            viewModel.searchProductsByName("")
            assertEquals(BridgeViewViewModelState.OnError(Throwable("{}")), viewModel.state.value)
        }
    }
}