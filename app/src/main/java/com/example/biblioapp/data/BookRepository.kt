package com.example.biblioapp.data

import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {

    val allFavorites: Flow<List<BookEntity>> = bookDao.getAllFavoriteBooks()

    suspend fun insert(book: BookEntity) {
        bookDao.insertBook(book)
    }

    suspend fun delete(book: BookEntity) {
        bookDao.deleteBook(book)
    }
}
