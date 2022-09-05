package com.pbasualdo.haystackphotosearch.ui

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.pbasualdo.haystackphotosearch.domain.Photo

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
        viewHolder: AbstractDetailsDescriptionPresenter.ViewHolder,
        item: Any
    ) {
        val movie = item as Photo

        viewHolder.title.text = movie.title
        viewHolder.subtitle.text = movie.studio
        viewHolder.body.text = movie.description
    }
}