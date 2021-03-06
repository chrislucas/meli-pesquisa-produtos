package com.br.apimercadolivre.searchproducts.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.apimercadolivre.R
import com.br.apimercadolivre.general.models.BridgeViewViewModelState
import com.br.apimercadolivre.searchproducts.models.models.Product
import com.br.apimercadolivre.searchproducts.models.models.ResultSearchProduct
import com.br.apimercadolivre.searchproducts.repositories.MeliSite
import com.br.apimercadolivre.searchproducts.ui.action.ChannelFragmentActivity
import com.br.apimercadolivre.searchproducts.ui.ext.onBackPressed
import com.br.apimercadolivre.searchproducts.ui.list.action.BinderAdapterProductViewHolder
import com.br.apimercadolivre.searchproducts.ui.list.action.InteractiveItemViewHolder
import com.br.apimercadolivre.searchproducts.ui.list.adapter.GenericAdapterRecyclerView
import com.br.apimercadolivre.searchproducts.viewmodels.SearchViewModel
import com.br.apimercadolivre.searchproducts.viewmodels.closeKeyboard
import com.br.apimercadolivre.searchproducts.viewmodels.factory.GenericViewModelFactory
import com.br.apimercadolivre.searchproducts.viewmodels.provider.ViewModelProviderUtils
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

    private lateinit var spinnerRegiosMercadoLivre: Spinner

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

        val viewModelFactory =
            GenericViewModelFactory(MeliSite::class.java, MeliSite.MERCADO_LIVRE_ARG)

        viewModel = ViewModelProviderUtils.get(
            viewModelStore, viewModelFactory, SearchViewModel::class.java
        )

        viewModel.state.observe(viewLifecycleOwner) { state ->
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

        spinnerRegiosMercadoLivre = viewRoot.findViewById(R.id.sp_sites)

        spinnerRegiosMercadoLivre.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.site = MeliSite.values()[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // NPTHING
                }
            }

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sites,
            R.layout.default_layout_item_spinner
        )

        spinnerRegiosMercadoLivre.adapter = adapter

    }

    override fun execute(data: Product) {
        channelFragmentActivity?.loadFragmentWithData(ProductDetailFragment.newInstance(data))
    }
}