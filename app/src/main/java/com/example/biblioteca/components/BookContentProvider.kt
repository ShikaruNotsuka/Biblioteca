package com.example.biblioteca.components

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.biblioteca.data.AppDatabase
import com.example.biblioteca.data.BookDao

class BookContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.biblioteca.provider"
        private const val BOOKS = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "favorite_books", BOOKS)
        }
    }

    private lateinit var bookDao: BookDao

    override fun onCreate(): Boolean {
        val context = context ?: return false
        val database = AppDatabase.getDatabase(context)
        bookDao = database.bookDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            BOOKS -> {
                val cursor = bookDao.getAllFavoriteBooksCursor()
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            BOOKS -> "vnd.android.cursor.dir/$AUTHORITY.favorite_books"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0
}
