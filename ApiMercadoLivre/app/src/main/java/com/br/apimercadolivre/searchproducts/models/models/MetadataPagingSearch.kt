package com.br.apimercadolivre.searchproducts.models.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MetadataPagingSearch(
    @SerializedName("total") val total: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("primary_results") val primaryResults: Int,
): Parcelable