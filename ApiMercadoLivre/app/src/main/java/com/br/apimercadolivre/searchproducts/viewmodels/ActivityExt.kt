package com.br.apimercadolivre.searchproducts.viewmodels

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Activity.closeKeyboard() {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    val view = this.currentFocus ?: View(this)
    inputMethodManager?.let {
        it.hideSoftInputFromWindow(view.windowToken, 0)
    }
}