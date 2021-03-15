package com.br.apimercadolivre.searchproducts.models.endpoint

import com.br.apimercadolivre.searchproducts.models.models.ResultSearchProduct
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MercadoLivreEndpoint {

    @GET("search")
    fun searchProductsByName(@Query("q") productName: String): Response<ResultSearchProduct>
}