package com.example.biblioteca.data

import com.google.gson.annotations.SerializedName

/* Es la respuesta que devuelve la API */
data class BookResponse(
    @SerializedName("items")
    val items: List<BookItem>? = null
)

/* Es el libro con su ID y la información del volumen */
data class BookItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo
)

/* Información detallada del libro */
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

/* URLs de las imágenes */
data class ImageLinks(
    @SerializedName("thumbnail")
    val thumbnail: String? = null
)
