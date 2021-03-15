package com.br.apimercadolivre.searchproducts.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.br.apimercadolivre.R
import com.br.apimercadolivre.searchproducts.models.models.Product


class ProductDetailFragment : Fragment() {

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getParcelable(PRODUCT_DETAIL)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            product = it.getParcelable(PRODUCT_DETAIL)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(PRODUCT_DETAIL, product)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_producr_detail, container, false)
        initViews(view)
        fillContentView()
        return view
    }

    private fun initViews(viewRoot: View) {

    }

    private fun fillContentView() {

    }

    companion object {
        private const val PRODUCT_DETAIL = "PRODUCT_DETAIL"

        @JvmStatic
        fun newInstance(product: Product) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PRODUCT_DETAIL, product)
                }
            }
    }
}