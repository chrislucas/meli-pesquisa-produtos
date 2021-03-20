package com.br.apimercadolivre.searchproducts.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.br.apimercadolivre.searchproducts.models.endpoint.MercadoLivreEndpoint
import com.br.apimercadolivre.searchproducts.models.models.ResultSearchProduct
import com.br.apimercadolivre.searchproducts.repositories.MeliSite
import com.br.apimercadolivre.searchproducts.repositories.ProdutoMercadoLivreRepository
import com.br.apimercadolivre.searchproducts.repositories.provideEndpoint
import com.br.apimercadolivre.utils.InstantCoroutineDispatcherRule.Companion.instantLiveDataAndCoroutineRule
import com.br.apimercadolivre.utils.fromJsonToObject
import com.br.apimercadolivre.utils.provideGsonInstance
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ProdutoMercadoLivreRepositoryTest {


    @get:Rule
    val archRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = instantLiveDataAndCoroutineRule


    @Before
    fun setup() {
        mockkStatic("com.br.apimercadolivre.general.http.providers.EndpointApiProvider")
    }


    @Test
    fun `ao pesquisar por um produto e a api retornar 200 deve existir uma lista de produtos de tamanhoa maior ou igual a 0`() {

        val rqSuccess: MercadoLivreEndpoint = mockk(relaxed = true)

        val result: ResultSearchProduct =
            provideGsonInstance().fromJsonToObject("search_product/result_search_product_1_item.json")

        every { provideEndpoint(any()) } returns rqSuccess

        coEvery { rqSuccess.searchProductsByName(any()) } returns Response.success(result)

        val repository = ProdutoMercadoLivreRepository(MeliSite.MERCADO_LIVRE_ARG)

        runBlocking {
            val response: Response<ResultSearchProduct> = repository.searchProductsByName("bike")
            assertTrue { response.isSuccessful }
            assertTrue { response.body()?.products?.size == 1 }
        }
    }

    @Test
    fun `ao pesquisar por um produto e a api devolver um erro devo receber um valor de erro via responseBody`() {

        val rqError: MercadoLivreEndpoint = mockk(relaxed = true)

        every { provideEndpoint(any()) } returns rqError

        coEvery { rqError.searchProductsByName(any()) } returns Response.error(
            500,
            ResponseBody.create(MediaType.parse("application/json"), "{}")
        )

        val repository = ProdutoMercadoLivreRepository(MeliSite.MERCADO_LIVRE_ARG)

        runBlocking {
            val response: Response<ResultSearchProduct> = repository.searchProductsByName("bike")
            assertFalse { response.isSuccessful }
            assertEquals(500, response.code())
        }
    }
}