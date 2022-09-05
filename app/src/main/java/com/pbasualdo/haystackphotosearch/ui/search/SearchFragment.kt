package com.pbasualdo.haystackphotosearch.ui.search

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.*
import com.pbasualdo.haystackphotosearch.domain.Photo
import com.pbasualdo.haystackphotosearch.ui.AppViewModelFactory
import com.pbasualdo.haystackphotosearch.ui.CardPresenter


class SearchFragment : SearchSupportFragment(), SearchSupportFragment.SearchResultProvider {
    private val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
    private val viewModel: SearchViewModel by viewModels { AppViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSearchResultProvider(this)
        setOnItemViewClickedListener(ItemViewClickedListener())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.photos.observe(viewLifecycleOwner){
            loadRows(it)
        }
    }

    private fun loadRows(photos: List<Photo>) {
        val cardPresenter = CardPresenter()

        if(photos.isEmpty()) {
            val header = HeaderItem(0, "No results were found for:  ${viewModel.searchQuery.value}")
            rowsAdapter.add(ListRow(header, ArrayObjectAdapter(cardPresenter)))
            return
        }



        val header = HeaderItem(0, "Result of: ${viewModel.searchQuery.value}")

        for (i in 0 until NUM_ROWS) {
//            if (i != 0) {
//                Collections.shuffle(list)
//            }
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            for (j in 0 until NUM_COLS) {
                if(j<= (NUM_COLS * i) + j)
                    listRowAdapter.add(photos[(NUM_COLS * i) + j])
            }
            //val header = HeaderItem(i.toLong(), MovieList.MOVIE_CATEGORY[i])
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }
    }

    override fun getResultsAdapter(): ObjectAdapter {
        return rowsAdapter
    }

    override fun onQueryTextChange(newQuery: String): Boolean {
        rowsAdapter.clear()
        if (!TextUtils.isEmpty(newQuery)) {
            viewModel.search(newQuery)
//            delayedLoad.setSearchQuery(newQuery)
//            handler.removeCallbacks(delayedLoad)
//            handler.postDelayed(delayedLoad, SEARCH_DELAY_MS)
        }
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        rowsAdapter.clear()
        if (!TextUtils.isEmpty(query)) {
            viewModel.search(query)
//            delayedLoad.setSearchQuery(query)
//            handler.removeCallbacks(delayedLoad)
//            handler.postDelayed(delayedLoad, SEARCH_DELAY_MS)
        }
        return true
    }

    private class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder, item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?, row: Row?
        ) {
            Toast.makeText(itemViewHolder.view.context, "Clicked on Result from the search", Toast.LENGTH_LONG)
                .show()
        }
    }

    companion object {
        private val TAG = "SearchFragment"
        private const val SEARCH_DELAY_MS = 300L
        private val BACKGROUND_UPDATE_DELAY = 300
        private val GRID_ITEM_WIDTH = 200
        private val GRID_ITEM_HEIGHT = 200
        private val NUM_ROWS = 6
        private val NUM_COLS = 15
    }
}