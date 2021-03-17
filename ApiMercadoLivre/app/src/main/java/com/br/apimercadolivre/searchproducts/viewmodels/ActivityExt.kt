package com.br.apimercadolivre.searchproducts.viewmodels

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import com.br.apimercadolivre.searchproducts.ui.fragments.WarningUserDialogFragment
import com.br.apimercadolivre.searchproducts.ui.fragments.WarningUserDialogFragment.Companion.TEXT_WARNING_KEY


fun Activity.closeKeyboard() {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    val view = this.currentFocus ?: View(this)
    inputMethodManager?.let {
        it.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun FragmentActivity.showDialogErrorMessage(tag: String, message: String? = null) {
    val dialog = WarningUserDialogFragment()
    if (message != null) {
        dialog.arguments = Bundle().also {
            it.putString(TEXT_WARNING_KEY, message)
        }
    }
    dialog.show(supportFragmentManager, tag)
}