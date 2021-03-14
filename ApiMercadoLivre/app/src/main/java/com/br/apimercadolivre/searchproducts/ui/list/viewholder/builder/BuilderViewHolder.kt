package com.br.apimercadolivre.searchproducts.ui.list.viewholder.builder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.br.apimercadolivre.R
import com.br.apimercadolivre.searchproducts.ui.list.viewholder.EmptyStateViewHolder
import com.br.apimercadolivre.searchproducts.ui.list.viewholder.ProductViewHolder
import java.lang.Exception
import java.lang.IllegalArgumentException

object BuilderViewHolder {

    @IntDef(
        VIEW_HOLDER_PRODUCT_LIST,
        VIEW_HOLDER_EMPTY_STATE
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class ViewType

    const val VIEW_HOLDER_EMPTY_STATE = 0
    const val VIEW_HOLDER_PRODUCT_LIST = 1

    @JvmStatic
    fun build(
        @ViewType viewType: Int,
        viewGroup: ViewGroup,
        defaultBuilder: (() -> RecyclerView.ViewHolder)? = null
    ): RecyclerView.ViewHolder {

        return when(viewType) {
            VIEW_HOLDER_EMPTY_STATE -> {
                EmptyStateViewHolder(getView(viewGroup, R.layout.layout_empty_state))
            }
            VIEW_HOLDER_PRODUCT_LIST -> {
                ProductViewHolder(getView(viewGroup, R.layout.layout_item_product))
            }
            else -> {
                defaultBuilder?.run {
                    this()
                } ?: throw IllegalArgumentException("")
            }
        }

    }

    private fun getView(viewGroup: ViewGroup, @LayoutRes layout: Int): View =
        LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)

}