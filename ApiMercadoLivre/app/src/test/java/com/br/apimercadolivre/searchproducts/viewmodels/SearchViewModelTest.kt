package com.br.apimercadolivre.searchproducts.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.br.apimercadolivre.general.models.BridgeViewViewModelState
import com.br.apimercadolivre.searchproducts.models.endpoint.MercadoLivreEndpoint
import com.br.apimercadolivre.searchproducts.models.models.ResultSearchProduct
import com.br.apimercadolivre.searchproducts.repositories.MeliSite
import com.br.apimercadolivre.searchproducts.repositories.ProdutoMercadoLivreRepository
import com.br.apimercadolivre.searchproducts.repositories.providerProdutoMercadoLivreRepository
import com.br.apimercadolivre.utils.TestCoroutineRule
import com.br.apimercadolivre.utils.fromJsonToObject
import com.br.apimercadolivre.utils.instantLiveDataAndCoroutineRule
import com.br.apimercadolivre.utils.provideGsonInstance
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


class SearchViewModelTest {

    //@get:Rule
    //val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    //@ExperimentalCoroutinesApi
    //@get:Rule
    //val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val archRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = instantLiveDataAndCoroutineRule


    @MockK
    private lateinit var repository: ProdutoMercadoLivreRepository
    @MockK
    private lateinit var viewModel: SearchViewModel

    private lateinit var result: ResultSearchProduct

    private lateinit var endpoint: MercadoLivreEndpoint


    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = mockk(relaxed = true)
        endpoint = mockk()
        viewModel = SearchViewModel()
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