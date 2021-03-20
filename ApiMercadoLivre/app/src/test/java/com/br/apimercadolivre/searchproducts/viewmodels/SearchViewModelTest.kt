package com.br.apimercadolivre.searchproducts.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.br.apimercadolivre.general.models.BridgeViewViewModelState
import com.br.apimercadolivre.searchproducts.models.models.ResultSearchProduct
import com.br.apimercadolivre.searchproducts.repositories.MeliSite
import com.br.apimercadolivre.searchproducts.repositories.ProdutoMercadoLivreRepository
import com.br.apimercadolivre.searchproducts.repositories.provideMercadoLivreRepository
import com.br.apimercadolivre.utils.InstantCoroutineDispatcherRule.Companion.instantLiveDataAndCoroutineRule
import com.br.apimercadolivre.utils.fromJsonToObject
import com.br.apimercadolivre.utils.provideGsonInstance
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
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


    private val result: ResultSearchProduct by lazy {
        provideGsonInstance().fromJsonToObject("search_product/result_search_product_1_item.json")
    }


    @Before
    fun setup() {
        mockkStatic(::provideMercadoLivreRepository)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `ao realizar uma requisicao, ela devolvendo uma lista de produtos estado da viewmodel deve sucesso`() =
        runBlockingTest {
            val (viewModel, dependencies) = provider()

            val repo: ProdutoMercadoLivreRepository = dependencies.repository

            val query = "corda de pular"

            val success = Response.success(result)

            val observer = mockk<Observer<BridgeViewViewModelState>>(relaxed = true)

            viewModel.state.observeForever(observer)

            every { provideMercadoLivreRepository(any()) } returns repo

            coEvery { repo.searchProductsByName(query) } returns success

            viewModel.searchProductsByName(query)

            coVerify {
                val response = BridgeViewViewModelState.OnSuccess(result)
                observer.onChanged(response)
            }
        }


    @Test
    fun `ao realizar uma requisicao a api deve retornar 500 e a viewmodel deve ficar no estado de erro`() {

        val (viewModel, dependencies) = provider()

        val error: Response<ResultSearchProduct> = Response.error(
            500,
            ResponseBody.create(MediaType.parse("application/json"), "{}")
        )

        val observer = mockk<Observer<BridgeViewViewModelState>>(relaxed = true)

        viewModel.state.observeForever(observer)

        every { provideMercadoLivreRepository(any()) } returns dependencies.repository

        coEvery {
            dependencies.repository.searchProductsByName(any())
        } returns error

        runBlocking {
            viewModel.searchProductsByName("carro")
        }

        coEvery {
            val response = BridgeViewViewModelState.OnError(Throwable("{}"))
            observer.onChanged(response)
        }
    }

    @Test
    fun `ao mudar o local de pesquisa de produtos deve-se retornar o site devido`() {
        val viewModel = SearchViewModel(MeliSite.MERCADO_LIVRE_BRA)
        viewModel.site = MeliSite.MERCADO_LIVRE_BRA

        assertEquals(MeliSite.MERCADO_LIVRE_BRA, viewModel.site, "Not equals")

    }
}


data class Dependencies(val repository: ProdutoMercadoLivreRepository)

data class MockDataViewModel(
    val viewModel: SearchViewModel = SearchViewModel(MeliSite.MERCADO_LIVRE_ARG),
    val dependencies: Dependencies
)


fun provider() = MockDataViewModel(
    SearchViewModel(MeliSite.MERCADO_LIVRE_BRA),
    Dependencies(mockk(relaxed = true))
)