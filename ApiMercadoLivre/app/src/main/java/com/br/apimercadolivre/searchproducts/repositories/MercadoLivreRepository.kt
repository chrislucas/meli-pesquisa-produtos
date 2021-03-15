package com.br.apimercadolivre.searchproducts.repositories

import com.br.apimercadolivre.general.http.providers.getApiEndpoint
import com.br.apimercadolivre.searchproducts.models.endpoint.MercadoLivreEndpoint

class MercadoLivreRepository(private val meliSite: MeliSite) {

    companion object {
        private const val BASE_URL_MELI_ENDPOINT = "https://api.mercadolibre.com/sites/"
    }

    private val api = getApiEndpoint(
        String.format("%s/%s", BASE_URL_MELI_ENDPOINT, meliSite.site),
        MercadoLivreEndpoint::class.java
    )

    suspend fun searchProductsByName(name: String) = api.searchProductsByName(name)
}

enum class MeliSite(val site: String) {
    MLA("MLA")
}