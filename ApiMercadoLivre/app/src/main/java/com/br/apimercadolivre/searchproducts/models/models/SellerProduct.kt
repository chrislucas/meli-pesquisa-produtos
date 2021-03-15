package com.br.apimercadolivre.searchproducts.models.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SellerProduct(
    @SerializedName("id") val id: String,
    @SerializedName("power_seller_status") val status: String
) : Parcelable
