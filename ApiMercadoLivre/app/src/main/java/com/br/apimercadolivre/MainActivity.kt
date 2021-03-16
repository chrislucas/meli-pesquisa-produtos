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
        setSupportActionBar(findViewById(R.id.default_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            replaceFragment(SearchFragment.newInstance())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        // manter somente um fragment na pila
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(fragment::class.java.name)
            .commit()
    }

    override fun loadFragmentWithData(fragment: Fragment) {
        replaceFragment(fragment)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            moveTaskToBack(true)
            finish()
        }
    }
}