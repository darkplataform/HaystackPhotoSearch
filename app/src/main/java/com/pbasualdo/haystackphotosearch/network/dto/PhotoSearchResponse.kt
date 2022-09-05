package com.pbasualdo.haystackphotosearch.network.dto

data class PhotoSearchResponse(
    val photos: PhotoResultContainer
)

data class PhotoResultContainer(
    val page: Int,
    val photo: List<PhotoElement>
)

data class PhotoElement(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val dateupload: Long,
    val ownername: String
)