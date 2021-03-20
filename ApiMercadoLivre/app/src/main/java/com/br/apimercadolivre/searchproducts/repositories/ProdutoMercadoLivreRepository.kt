package com.br.apimercadolivre.searchproducts.repositories

import com.br.apimercadolivre.general.http.providers.getApiEndpoint
import com.br.apimercadolivre.searchproducts.models.endpoint.MercadoLivreEndpoint

class ProdutoMercadoLivreRepository(private val meliSite: MeliSite) {

    var site: String = meliSite.site

    companion object {
        private const val BASE_URL_MELI_ENDPOINT = "https://api.mercadolibre.com/sites/"
    }


    private val api: MercadoLivreEndpoint
        get() =
            getApiEndpoint(
                String.format("%s/%s/", BASE_URL_MELI_ENDPOINT, site),
                MercadoLivreEndpoint::class.java
            )


    suspend fun searchProductsByName(name: String) = api.searchProductsByName(name)
}