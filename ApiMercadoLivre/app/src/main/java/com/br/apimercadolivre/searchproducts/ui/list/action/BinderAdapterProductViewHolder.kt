package com.br.apimercadolivre.searchproducts.ui.list.action

import android.net.Uri
import android.view.Gravity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.br.apimercadolivre.R
import com.br.apimercadolivre.searchproducts.models.models.Product
import com.br.apimercadolivre.searchproducts.ui.list.viewholder.ProductViewHolder
import com.br.apimercadolivre.searchproducts.ui.list.viewholder.builder.BuilderViewHolder
import com.br.apimercadolivre.searchproducts.ui.ext.callbackLoadImagePolicyOffline
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso


class BinderAdapterProductViewHolder(
    private val interactiveItemViewHolder: InteractiveItemViewHolder<Product>,
) : BinderAdapterToViewHolder<Product> {

    private var imageLoader: Picasso = Picasso.get()

    override fun onClick(viewHolder: RecyclerView.ViewHolder, data: List<Product>) {
        when (viewHolder) {
            is ProductViewHolder -> {
                if (data.isNotEmpty()) {
                    viewHolder.itemView.setOnClickListener {
                        interactiveItemViewHolder.execute(data[viewHolder.adapterPosition])
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
                        .centerCrop(Gravity.CENTER)
                        .resize(50, 50)
                        .error(R.drawable.question)
                        .into(
                            ivProductImage,
                            imageLoader.callbackLoadImagePolicyOffline(
                                R.drawable.question,
                                uri,
                                ivProductImage
                            )
                        )
                }
            }
            else -> {
                // DO NOTHING
            }
        }
    }


    override fun getViewHolder(viewType: Int, viewRoot: ViewGroup): RecyclerView.ViewHolder =
        BuilderViewHolder.build(viewType, viewRoot)
}