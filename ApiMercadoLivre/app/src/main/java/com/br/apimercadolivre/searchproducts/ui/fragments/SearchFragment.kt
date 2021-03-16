package com.br.apimercadolivre.searchproducts.ui.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.apimercadolivre.R
import com.br.apimercadolivre.general.models.BridgeViewViewModelState
import com.br.apimercadolivre.searchproducts.models.models.Product
import com.br.apimercadolivre.searchproducts.models.models.ResultSearchProduct
import com.br.apimercadolivre.searchproducts.models.models.SellerProduct
import com.br.apimercadolivre.searchproducts.ui.action.ChannelFragmentActivity
import com.br.apimercadolivre.searchproducts.ui.ext.onBackPressed
import com.br.apimercadolivre.searchproducts.ui.list.action.BinderAdapterProductViewHolder
import com.br.apimercadolivre.searchproducts.ui.list.action.InteractiveItemViewHolder
import com.br.apimercadolivre.searchproducts.ui.list.adapter.GenericAdapterRecyclerView
import com.br.apimercadolivre.searchproducts.viewmodels.SearchViewModel
import com.br.apimercadolivre.searchproducts.viewmodels.closeKeyboard
import com.br.apimercadolivre.searchproducts.viewmodels.showDialogErrorMessage
import timber.log.Timber

class SearchFragment : Fragment(), InteractiveItemViewHolder<Product> {

    companion object {
        fun newInstance() = SearchFragment()

        const val PRODUCTS_BUNDLE_KEY = "PRODUCTS_BUNDLE_KEY"
        private const val TAG = "SEARCH_FRAGMENT"
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

        viewModel.state.observe(this) { state ->
            when (state) {
                is BridgeViewViewModelState.OnSuccess<*> -> {
                    val data = state.value as ResultSearchProduct
                    if (data.products.isNotEmpty()) {
                        adapterRecyclerView.updateCollection(data.products.toMutableList())
                    } else {
                        activity?.showDialogErrorMessage(
                            TAG,
                            resources.getString(R.string.text_warning_user_empty_product_list)
                        )
                    }
                }
                is BridgeViewViewModelState.OnError -> {
                    activity?.showDialogErrorMessage(
                        TAG,
                        resources.getString(R.string.text_warning_user_error_after_search_product)
                    )
                }
            }
        }
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
        view.onBackPressed { true }
        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ChannelFragmentActivity) {
            channelFragmentActivity = context
        }
    }

    private fun initViews(viewRoot: View) {
        recyclerView = viewRoot.findViewById(R.id.rv_products)
        recyclerView.let {
            it.adapter = adapterRecyclerView
            it.layoutManager = LinearLayoutManager(this.requireContext())
        }
        searchView = viewRoot.findViewById(R.id.sv_widget)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    Timber.i(query)
                    viewModel.searchProductsByName(query)
                }
                activity?.closeKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // DO NOTHING
                return false
            }
        })
    }

    override fun execute(data: Product) {
        channelFragmentActivity?.loadFragmentWithData(ProductDetailFragment.newInstance(data))
    }
}