package com.example.biblioteca.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val authors: String,
    val description: String?,
    val imageUrl: String?
)
