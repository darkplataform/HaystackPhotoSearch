package com.pbasualdo.haystackphotosearch.ui.search

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.pbasualdo.haystackphotosearch.R

/**
 * Loads [MainFragment].
 */
class SearchActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_browse_fragment, SearchFragment())
                .commitNow()
        }
    }
}