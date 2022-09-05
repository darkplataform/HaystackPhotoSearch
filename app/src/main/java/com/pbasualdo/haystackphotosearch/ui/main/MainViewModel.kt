package com.pbasualdo.haystackphotosearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbasualdo.haystackphotosearch.domain.Photo
import com.pbasualdo.haystackphotosearch.network.NetworkService
import com.pbasualdo.haystackphotosearch.network.dto.PhotoElement
import com.pbasualdo.haystackphotosearch.ui.MovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class MainViewModel(
    private val networkService: NetworkService
): ViewModel() {

    private var count: Long = 0

    val photos: MutableLiveData<List<Photo>> = MutableLiveData()

    init {
        photos.value = runBlocking {
            networkService.photos.getRecent().photos.photo.map {
                buildMovieInfo(
                    it.title,
                    it.ownername + "/" + MovieList.getShortDate(it.dateupload),
                    it.ownername + "/" + MovieList.getShortDate(it.dateupload),
                    "https://farm${it.farm}.staticflickr.com/${it.server}/${it.id}_${it.secret}.jpg",
                    "https://farm${it.farm}.staticflickr.com/${it.server}/${it.id}_${it.secret}.jpg",
                    "https://farm${it.farm}.staticflickr.com/${it.server}/${it.id}_${it.secret}.jpg"
                )
            }

        }
    }

    private fun buildMovieInfo(
        title: String,
        description: String,
        studio: String,
        videoUrl: String,
        cardImageUrl: String,
        backgroundImageUrl: String
    ): Photo {
        val movie = Photo()
        movie.id = count++
        movie.title = title
        movie.description = description
        movie.studio = studio
        movie.cardImageUrl = cardImageUrl
        movie.backgroundImageUrl = backgroundImageUrl
        movie.videoUrl = videoUrl
        return movie
    }

    fun getShortDate(ts:Long?):String{
        if(ts == null) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts
        //return formatted date
        return android.text.format.DateFormat.format("E, dd MMM yyyy", calendar).toString()
    }

}