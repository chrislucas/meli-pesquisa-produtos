package com.br.apimercadolivre.searchproducts.models.models

import com.google.gson.annotations.SerializedName

data class ResultSearchProduct(
    @SerializedName("site_id") val siteId: String,
    @SerializedName("query") val query: String,
    @SerializedName("paging") val metadataPaging: MetadataPagingSearch,
    @SerializedName("results") val products: List<Product>
)