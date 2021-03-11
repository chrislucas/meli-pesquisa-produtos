package com.br.apimercadolivre.models.endpoint

import com.br.apimercadolivre.models.models.ResultSearchProduct
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MercadoLivreEndpoint {

    @GET("search")
    fun searchProductsByName(@Query("q") productName: String): Call<ResultSearchProduct>
}