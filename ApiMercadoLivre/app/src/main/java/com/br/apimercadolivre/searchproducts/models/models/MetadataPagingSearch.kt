package com.br.apimercadolivre.searchproducts.models.models

import com.google.gson.annotations.SerializedName

data class MetadataPagingSearch(
    @SerializedName("total") val total: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("primary_results") val primaryResults: Int,
)