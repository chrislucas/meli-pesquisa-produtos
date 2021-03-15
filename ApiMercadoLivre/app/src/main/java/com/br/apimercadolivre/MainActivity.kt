package com.br.apimercadolivre

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.br.apimercadolivre.searchproducts.ui.action.ChannelFragmentActivity
import com.br.apimercadolivre.searchproducts.ui.fragments.SearchFragment

class MainActivity : AppCompatActivity(), ChannelFragmentActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            replaceFragment(SearchFragment.newInstance())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

    override fun loadFragmentWithData(fragment: Fragment) {
        replaceFragment(fragment)
    }
}