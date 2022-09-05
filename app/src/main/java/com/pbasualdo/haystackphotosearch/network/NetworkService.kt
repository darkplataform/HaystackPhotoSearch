package com.pbasualdo.haystackphotosearch.network

import com.pbasualdo.haystackphotosearch.network.dto.PhotoSearchResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class NetworkService {

    private val retrofit by lazy{
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder().apply {
            addInterceptor(interceptor())
            readTimeout(5, TimeUnit.SECONDS)
            connectTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
        }




        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/services/rest/")
            .client(httpClient.build())
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
        retrofitBuilder.build()
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level= HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    val photos
        get()=retrofit.create(PhotosService::class.java)
}

interface PhotosService {
    @GET("?method=flickr.photos.search&api_key=d234dfe38bf338134b96b2f8d7aeb265&format=json&nojsoncallback=1&extras=date_upload,owner_name")
    suspend fun searchPhotos(@Query("text") query: String) : PhotoSearchResponse

    @GET("?method=flickr.photos.getRecent&api_key=d234dfe38bf338134b96b2f8d7aeb265&nojsoncallback=1&format=json&extras=date_upload,owner_name")
    suspend fun getRecent() : PhotoSearchResponse
}