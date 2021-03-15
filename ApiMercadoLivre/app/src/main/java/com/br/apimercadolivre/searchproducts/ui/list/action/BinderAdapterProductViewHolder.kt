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


class BinderAdapterProductViewHolder(
    private val interactiveItemViewHolder: InteractiveItemViewHolder<Product>,
) : BinderAdapterToViewHolder<Product> {

    private var imageLoader: Picasso = Picasso.get()

    override fun onClick(viewHolder: RecyclerView.ViewHolder, data: List<Product>) {
        when (viewHolder) {
            is ProductViewHolder -> {
                if (data.isNotEmpty()) {
                    val position =
                        if (viewHolder.adapterPosition < 0) 0 else viewHolder.adapterPosition
                    viewHolder.itemView.setOnClickListener {
                        interactiveItemViewHolder.execute(data[position])
                    }
                }
            }
            else -> {
                // do nothing
            }
        }
    }

    override fun getItemViewType(data: Product?): Int {
        return data?.let { BuilderViewHolder.VIEW_HOLDER_PRODUCT_LIST }
            ?: BuilderViewHolder.VIEW_HOLDER_EMPTY_STATE
    }

    override fun fillFieldsInViewHolder(viewHolder: RecyclerView.ViewHolder, product: Product) {
        when (viewHolder) {
            is ProductViewHolder -> {
                viewHolder.apply {

                    this.tvProductName.text =
                        this.itemView.context.resources.getString(
                            R.string.txt_label_product_name,
                            product.name
                        )
                    this.tvProductPrice.text =
                        this.itemView.context.resources.getString(
                            R.string.txt_label_product_price,
                            product.price
                        )

                    val uri = Uri.parse(product.urlThumbnail)
                    imageLoader.load(uri)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .error(R.drawable.question)
                        .into(ivProductImage, callbackLoadImage(uri, ivProductImage))
                }
            }
            else -> {
                // DO NOTHING
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