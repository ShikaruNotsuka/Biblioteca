package com.example.biblioapp.data

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("items")
    val items: List<BookItem>? = null
)

data class BookItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    @SerializedName("title")
    val title: String,
    @SerializedName("authors")
    val authors: List<String>? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("publishedDate")
    val publishedDate: String? = null,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks? = null
)

data class ImageLinks(
    @SerializedName("thumbnail")
    val thumbnail: String? = null
)
