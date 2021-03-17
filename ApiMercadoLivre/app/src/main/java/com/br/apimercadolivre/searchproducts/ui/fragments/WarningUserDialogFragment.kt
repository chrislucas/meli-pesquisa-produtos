package com.br.apimercadolivre.searchproducts.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.br.apimercadolivre.R
import com.google.android.material.textview.MaterialTextView

class WarningUserDialogFragment : DialogFragment() {

    companion object {
        const val TEXT_WARNING_KEY = "TEXT_WARNING_KEY"
    }

    private var textWarning: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_warning_user_dialog_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Custom_Rounded_Dialog_Fragment)
        arguments?.let {
            textWarning = it.getString(TEXT_WARNING_KEY)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonDismiss = view.findViewById<Button>(R.id.bt_dismiss_dialog)
        buttonDismiss.setOnClickListener { dismiss() }

        textWarning?.let {
            view.findViewById<MaterialTextView>(R.id.tv_text_warning_user).text = it
        }
    }
}