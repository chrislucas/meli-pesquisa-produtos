package com.br.apimercadolivre.searchproducts.models.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("title") val name: String,
    @SerializedName("seller") val seller: SellerProduct,
    @SerializedName("price") val price: Double,
    @SerializedName("available_quantity") private val availableQuantity: Int,
    @SerializedName("thumbnail")  val urlThumbnail: String
) : Parcelable
