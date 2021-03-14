package com.br.apimercadolivre

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.br.apimercadolivre.searchproducts.ui.fragments.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance())
                .commitNow()
        }
    }
}