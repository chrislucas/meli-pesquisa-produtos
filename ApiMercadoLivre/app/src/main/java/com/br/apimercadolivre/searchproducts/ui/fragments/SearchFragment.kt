package com.br.apimercadolivre.searchproducts.ui.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.apimercadolivre.R
import com.br.apimercadolivre.searchproducts.models.models.Product
import com.br.apimercadolivre.searchproducts.models.models.SellerProduct
import com.br.apimercadolivre.searchproducts.ui.action.ChannelFragmentActivity
import com.br.apimercadolivre.searchproducts.ui.list.action.BinderAdapterProductViewHolder
import com.br.apimercadolivre.searchproducts.ui.list.action.InteractiveItemViewHolder
import com.br.apimercadolivre.searchproducts.ui.list.adapter.GenericAdapterRecyclerView
import com.br.apimercadolivre.searchproducts.viewmodels.SearchViewModel

class SearchFragment private constructor() : Fragment(), InteractiveItemViewHolder<Product> {

    companion object {
        fun newInstance() = SearchFragment()

        const val PRODUCTS_BUNDLE_KEY = "PRODUCTS_BUNDLE_KEY"
    }

    private lateinit var viewModel: SearchViewModel

    private lateinit var searchView: SearchView

    private var channelFragmentActivity: ChannelFragmentActivity? = null

    private lateinit var recyclerView: RecyclerView
    private val adapterRecyclerView: GenericAdapterRecyclerView<Product>
    private val binder = BinderAdapterProductViewHolder(this)
    private var products: ArrayList<Product> = arrayListOf()

    init {
        adapterRecyclerView = GenericAdapterRecyclerView(
            products,
            binder
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            products =
                it.getParcelableArrayList(PRODUCTS_BUNDLE_KEY)
                    ?: throw Exception("Llsta de produtos nula")
        }
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(PRODUCTS_BUNDLE_KEY, products)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
        initViews(view)
        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ChannelFragmentActivity) {
            channelFragmentActivity = context
        }
    }

    private fun initViews(viewRoot: View) {
        searchView = viewRoot.findViewById(R.id.sv_widget)
        recyclerView = viewRoot.findViewById(R.id.rv_products)

        recyclerView.let {
            it.adapter = adapterRecyclerView
            it.layoutManager = LinearLayoutManager(this.requireContext())
        }

        fn()
    }

    private fun fn() {

        val products = mutableListOf<Product>()
        arrayOf(
            Product(
                "A",
                SellerProduct("1", "0xff"),
                1000,
                1,
                "https://http2.mlstatic.com/storage/developers-site-cms-admin/322395477947-logo--large-plus-v2.png"
            ),
            Product(
                "B",
                SellerProduct("3", "0xf2"),
                1000,
                10,
                "https://http2.mlstatic.com/storage/developers-site-cms-admin/322395477947-logo--large-plus-v2.png"
            ),
            Product(
                "Baskjdhaskjdhaksjdhaksjhdaskjhd",
                SellerProduct("30", "0xf2"),
                1000000000,
                999999999,
                "https://http2.mlstatic.com/storage/developers-site-cms-admin/322395477947-logo--large-plus-v2.png"
            )
        ).forEach {
            products.add(it)
        }
        var i = 0
        while (i < (1 shl 20))
            i++

        adapterRecyclerView.updateCollection(products)
    }

    override fun execute(data: Product) {
        channelFragmentActivity?.loadFragmentWithData(ProductDetailFragment.newInstance(data))
    }
}