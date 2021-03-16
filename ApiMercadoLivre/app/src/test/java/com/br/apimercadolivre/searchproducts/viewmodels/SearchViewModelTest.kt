package com.br.apimercadolivre.searchproducts.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.br.apimercadolivre.general.models.BridgeViewViewModelState
import com.br.apimercadolivre.searchproducts.models.endpoint.MercadoLivreEndpoint
import com.br.apimercadolivre.searchproducts.models.models.ResultSearchProduct
import com.br.apimercadolivre.searchproducts.repositories.MeliSite
import com.br.apimercadolivre.searchproducts.repositories.ProdutoMercadoLivreRepository
import com.br.apimercadolivre.utils.*
import com.br.apimercadolivre.utils.InstantCoroutineDispatcherRule.Companion.instantLiveDataAndCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals


class SearchViewModelTest {

    @get:Rule
    val archRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val rule = instantLiveDataAndCoroutineRule

    @ExperimentalCoroutinesApi
    @get:Rule
    val scope = InstantCoroutineDispatcherRule()


    @MockK
    private lateinit var repository: ProdutoMercadoLivreRepository

    @MockK
    private lateinit var viewModel: SearchViewModel

    private lateinit var result: ResultSearchProduct

    @MockK
    private lateinit var endpoint: MercadoLivreEndpoint


    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = SearchViewModel(MeliSite.MERCADO_LIVRE_ARG)
    }


    @Test
    fun `ao realizar uma requisicao, ela devolvendo uma lista de produtos estado da viewmodel deve sucesso`() {

        result = provideGsonInstance().fromJsonToObject("search_product/result_search_product.json")

        val query = "corda de pular"

        coEvery { repository.searchProductsByName(any()) } returns Response.success(result)

        coEvery { endpoint.searchProductsByName(any()) } returns Response.success(result)


        runBlocking {

            viewModel.searchProductsByName(query)
            assertEquals(BridgeViewViewModelState.OnSuccess(result), viewModel.state.value)
        }
    }


    @Test
    fun `ao realizar uma requisicao a api deve retornar 500 e a viewmodel deve ficar no estado de erro`() {
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