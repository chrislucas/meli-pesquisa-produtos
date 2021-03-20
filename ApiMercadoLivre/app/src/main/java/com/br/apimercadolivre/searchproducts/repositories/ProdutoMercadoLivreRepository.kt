package com.br.apimercadolivre.searchproducts.repositories

import com.br.apimercadolivre.general.http.providers.getApiEndpoint
import com.br.apimercadolivre.searchproducts.models.endpoint.MercadoLivreEndpoint

class ProdutoMercadoLivreRepository(meliSite: MeliSite) {

    var site: String = meliSite.site

    companion object {
        private const val BASE_URL_MELI_ENDPOINT = "https://api.mercadolibre.com/sites/"
    }

    private val api: MercadoLivreEndpoint by lazy {
        provideEndpoint(
            String.format(
                "%s/%s/",
                BASE_URL_MELI_ENDPOINT,
                site
            )
        )
    }


    suspend fun searchProductsByName(name: String) = api.searchProductsByName(name)
}

fun provideEndpoint(url: String) = getApiEndpoint(url, MercadoLivreEndpoint::class.java)