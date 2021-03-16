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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.Dispatcher
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import java.lang.Exception
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class SearchViewModelTest {

    @get:Rule
    val archRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = instantLiveDataAndCoroutineRule


    @MockK
    private lateinit var repository: ProdutoMercadoLivreRepository

    @MockK
    private lateinit var viewModel: SearchViewModel

    @MockK
    private lateinit var endpoint: MercadoLivreEndpoint

    private val result: ResultSearchProduct by lazy {
        provideGsonInstance().fromJsonToObject("search_product/result_search_product_1_item.json")
    }


    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = SearchViewModel()
        repository = mockk()
        endpoint = mockk()

        //mockkStatic("com.br.apimercadolivre.general.http.providers.EndpointApiProviderKt")

    }


    @Test
    fun `ao realizar uma requisicao, ela devolvendo uma lista de produtos estado da viewmodel deve sucesso`() {

        val query = "corda de pular"

        val success = Response.success(result)

        coEvery { repository.searchProductsByName(query) } returns success

        coEvery { endpoint.searchProductsByName(any()) } returns success

        runBlocking {
            viewModel.searchProductsByName(query)
            val actual = viewModel.state.value
            assertTrue { actual is BridgeViewViewModelState.OnSuccess<*> }
        }
    }


    @Test
    fun `ao realizar uma requisicao a api deve retornar 500 e a viewmodel deve ficar no estado de erro`() {

        val error: Response<ResultSearchProduct> = Response.error(
            500,
            ResponseBody.create(MediaType.parse("application/json"), "{}")
        )

        coEvery {
            repository.searchProductsByName(any())
        } returns error

        runBlocking {
            viewModel.searchProductsByName("")
            val actual = viewModel.state.value
            assertTrue { actual is BridgeViewViewModelState.OnError }
        }
    }
}