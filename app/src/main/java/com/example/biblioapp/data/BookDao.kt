package com.example.biblioapp.data

import android.database.Cursor
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Delete
    suspend fun deleteBook(book: BookEntity)

    @Query("SELECT * FROM favorite_books")
    fun getAllFavoriteBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM favorite_books")
    fun getAllFavoriteBooksCursor(): Cursor
}
