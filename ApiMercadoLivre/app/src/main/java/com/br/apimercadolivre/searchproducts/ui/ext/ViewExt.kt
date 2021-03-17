package com.br.apimercadolivre.searchproducts.ui.ext

import android.view.KeyEvent
import android.view.View


fun View.onBackPressed(action: () -> Unit)  {
    isFocusableInTouchMode = true
    //requestFocus()
    setOnKeyListener { _, keyCode, _ ->
        when(keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                action()
                true
            }

            else -> {
                // DO  NOTHING
                false
            }
        }
    }
}