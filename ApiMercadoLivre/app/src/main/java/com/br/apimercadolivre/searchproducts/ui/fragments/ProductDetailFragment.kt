package com.br.apimercadolivre.searchproducts.ui.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.br.apimercadolivre.R
import com.br.apimercadolivre.searchproducts.models.models.Product
import com.br.apimercadolivre.searchproducts.ui.ext.callbackLoadImagePolicyOffline
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso


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
        fillContentView(view)
        return view
    }


    private fun fillContentView(viewRoot: View) {
        product?.run {
            val imageViewProduct =
                viewRoot.findViewById<ImageView>(R.id.img_view_img_product_detail)
            val pic = Picasso.get()
            val uri = Uri.parse(this.urlThumbnail)
            pic.load(uri)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.question)
                .into(
                    imageViewProduct,
                    pic.callbackLoadImagePolicyOffline(
                        R.drawable.question,
                        uri,
                        imageViewProduct
                    )
                )

            val tvProductName =
                viewRoot.findViewById<MaterialTextView>(R.id.label_product_name_detail)

            tvProductName.text =
                resources.getString(
                    R.string.txt_label_product_name,
                    this.name
                )

            val tvProductPrice =
                viewRoot.findViewById<MaterialTextView>(R.id.label_product_price_detail)
            tvProductPrice.text =
                resources.getString(
                    R.string.txt_label_product_price,
                    this.price
                )
        }

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