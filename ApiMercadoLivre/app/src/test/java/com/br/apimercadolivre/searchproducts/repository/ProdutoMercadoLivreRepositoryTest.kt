package com.br.apimercadolivre.searchproducts.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.br.apimercadolivre.general.http.providers.getApiEndpoint
import com.br.apimercadolivre.searchproducts.models.endpoint.MercadoLivreEndpoint
import com.br.apimercadolivre.searchproducts.models.models.ResultSearchProduct
import com.br.apimercadolivre.searchproducts.repositories.MeliSite
import com.br.apimercadolivre.searchproducts.repositories.ProdutoMercadoLivreRepository
import com.br.apimercadolivre.utils.InstantCoroutineDispatcherRule.Companion.instantLiveDataAndCoroutineRule
import com.br.apimercadolivre.utils.fromJsonToObject
import com.br.apimercadolivre.utils.provideGsonInstance
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.spy
import retrofit2.Response
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class ProdutoMercadoLivreRepositoryTest {


    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    //@ExperimentalCoroutinesApi
    //@get:Rule
    //val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val archRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = instantLiveDataAndCoroutineRule


    @MockK
    lateinit var rqSuccess: MercadoLivreEndpoint


    @MockK
    lateinit var rqError: MercadoLivreEndpoint


    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        val result: ResultSearchProduct =
            provideGsonInstance().fromJsonToObject("search_product/result_search_product.json")

        coEvery { rqSuccess.searchProductsByName(any()) } returns Response.success(result)

        coEvery { rqError.searchProductsByName(any()) } returns Response.error(
            500,
            ResponseBody.create(MediaType.parse("application/json"), "{}")
        )

        mockkStatic("com.br.apimercadolivre.general.http.providers.EndpointApiProviderKt")
    }


    @Test
    fun `ao pesquisar por um produto e a api retornar 200 deve existir uma lista de produtos de tamanhoa maior ou igual a 0`() {
        val repository = ProdutoMercadoLivreRepository(MeliSite.MLA)

        val mock = spyk(repository, recordPrivateCalls = true)

        every { mock getProperty "api" } returns rqSuccess
        //propertyType MercadoLivreEndpoint::class answers { rqSuccess }

        runBlocking {
            val response: Response<ResultSearchProduct> = repository.searchProductsByName("Topper")

            assertTrue { response.isSuccessful }
            assertTrue { response.body()?.products?.size == 50 }
        }
    }

    @Test
    fun `ao pesquisar por um produto e a api devolver um erro devo receber um valor de erro via responseBody`() {
        val repository = ProdutoMercadoLivreRepository(MeliSite.MLA)

        val mock = spyk(repository, recordPrivateCalls = true)

        every { mock getProperty "api" } returns rqError

        runBlocking {
            val response: Response<ResultSearchProduct> = repository.searchProductsByName("Topper")
            assertFalse { response.isSuccessful }
        }
    }

}