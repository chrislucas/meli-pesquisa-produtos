package com.br.apimercadolivre.searchproducts.ui.list.action

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.br.apimercadolivre.R
import com.br.apimercadolivre.searchproducts.models.models.Product
import com.br.apimercadolivre.searchproducts.ui.list.viewholder.ProductViewHolder
import com.br.apimercadolivre.searchproducts.ui.list.viewholder.builder.BuilderViewHolder
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.lang.Exception


interface ActionOnCLick<T> {
    fun execute(data: T)
}

class BinderAdapterProductViewHolder(
    private val actionOnCLick: ActionOnCLick<Product>, private val context: Context
) : BinderAdapterToViewHolder<Product> {

    private var imageLoader: Picasso = Picasso.get()

    var getTyoeDefault = true

    override fun onClick(viewHolder: RecyclerView.ViewHolder, data: List<Product>) {
        when (viewHolder) {
            is ProductViewHolder -> {
                actionOnCLick.execute(data[viewHolder.adapterPosition])
            }
            else -> {
                // do nothing
            }
        }
    }

    override fun getItemViewType(data: Product?): Int {
        return if (getTyoeDefault || data == null) {
            BuilderViewHolder.VIEW_HOLDER_EMPTY_STATE
        } else {
            BuilderViewHolder.VIEW_HOLDER_PRODUCT_LIST
        }
    }

    override fun fillFieldsInViewHolder(viewHolder: RecyclerView.ViewHolder, product: Product) {
        when (viewHolder) {
            is ProductViewHolder -> {
                viewHolder.apply {
                    this.tvProductName.text =
                        context.resources.getString(R.string.txt_label_product_name, product.name)
                    this.tvProductPrice.text =
                        context.resources.getString(R.string.txt_label_product_price, product.price)

                    val uri = Uri.parse(product.urlThumbnail)
                    imageLoader.load(uri)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .error(R.drawable.question)
                        .into(ivProductImage, callbackLoadImage(uri, ivProductImage))
                }
            }
        }
    }

    private fun callbackLoadImage(uri: Uri, view: ImageView): Callback {
        return object : Callback {
            override fun onSuccess() {
                // DO NOTHING
            }

            override fun onError(e: Exception?) {
                imageLoader.load(uri)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .error(R.drawable.question)
                    .into(view, callbackLoadImage())
            }
        }
    }

    private fun callbackLoadImage() =
        object : Callback {
            override fun onSuccess() {
                Timber.i("IMG_LOADER_SUCCESSED")
            }

            override fun onError(e: Exception?) {
                Timber.e("IMG_LOADER_FAILED")
            }
        }


    override fun getViewHolder(viewType: Int, viewRoot: ViewGroup): RecyclerView.ViewHolder =
        BuilderViewHolder.build(viewType, viewRoot)
}