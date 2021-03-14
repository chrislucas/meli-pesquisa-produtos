package com.br.apimercadolivre.searchproducts.ui.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.br.apimercadolivre.searchproducts.ui.list.action.BinderAdapterToViewHolder

class GenericAdapterRecyclerView<T>(
    val collection: MutableList<T>,
    private val action: BinderAdapterToViewHolder<T>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = action.getViewHolder(viewType, parent)
        action.onClick(viewHolder, collection)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        action.fillFieldsInViewHolder(holder, collection[position])
    }

    override fun getItemCount(): Int = collection.size
}