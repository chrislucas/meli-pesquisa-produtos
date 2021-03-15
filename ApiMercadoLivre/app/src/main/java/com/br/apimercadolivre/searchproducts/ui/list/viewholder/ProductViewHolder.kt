package com.br.apimercadolivre.searchproducts.ui.list.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.apimercadolivre.R
import com.br.apimercadolivre.searchproducts.models.models.Product

class ProductViewHolder(private val layoutView: View) : RecyclerView.ViewHolder(layoutView) {

    private val viewRoot: View
    val ivProductImage: ImageView
    val tvProductName: TextView
    val tvProductPrice: TextView

    init {
        with(layoutView) {
            viewRoot = this.findViewById(R.id.cv_root_item_product_list)
            ivProductImage = this.findViewById(R.id.img_view_img_product)
            tvProductName = this.findViewById(R.id.tv_label_product_name)
            tvProductPrice = this.findViewById(R.id.tv_label_product_price)
        }
    }

}