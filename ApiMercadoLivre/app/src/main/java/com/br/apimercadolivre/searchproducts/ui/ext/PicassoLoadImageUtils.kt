package com.br.apimercadolivre.searchproducts.ui.ext

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.lang.Exception

fun Picasso.callbackLoadImagePolicyOffline(
    @DrawableRes errorResId: Int,
    uri: Uri,
    view: ImageView
): Callback {
    val ref = this
    return object : Callback {
        override fun onSuccess() {
            // DO NOTHING
        }

        override fun onError(e: Exception?) {
            ref.load(uri)
                .error(errorResId)
                .into(view, simpleLogCallbackLoadImage())
        }
    }
}

private fun simpleLogCallbackLoadImage() =
    object : Callback {
        override fun onSuccess() {
            Timber.i("IMG_LOADER_SUCCESSED")
        }

        override fun onError(e: Exception?) {
            Timber.e("IMG_LOADER_FAILED")
        }
    }