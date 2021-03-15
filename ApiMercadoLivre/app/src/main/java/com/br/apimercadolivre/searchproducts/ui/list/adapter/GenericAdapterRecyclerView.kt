package com.br.apimercadolivre.searchproducts.ui.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.br.apimercadolivre.searchproducts.ui.list.action.BinderAdapterToViewHolder
import com.br.apimercadolivre.searchproducts.ui.list.viewholder.builder.BuilderViewHolder

class GenericAdapterRecyclerView<T>(
    private val collection: MutableList<T>,
    private val action: BinderAdapterToViewHolder<T>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = action.getViewHolder(viewType, parent)
        action.onClick(viewHolder, collection)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (collection.isNotEmpty()) {
            action.fillFieldsInViewHolder(holder, collection[position])
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (collection.isNotEmpty()) {
            action.getItemViewType(collection[position])
        } else {
            BuilderViewHolder.VIEW_HOLDER_EMPTY_STATE
        }

    override fun getItemCount(): Int = if (collection.isEmpty()) 1 else collection.size

    fun updateCollection(mutableList: MutableList<T>) {
        collection.clear()
        collection.addAll(mutableList)
        this.notifyDataSetChanged()
    }
}