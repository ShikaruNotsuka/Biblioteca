package com.example.biblioteca.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biblioteca.data.BookEntity
import com.example.biblioteca.data.BookItem
import com.example.biblioteca.data.BookRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    // Lista de todos los favoritos expuesta como StateFlow
    val favoriteBooks: StateFlow<List<BookEntity>> = repository.allFavorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Función para añadir a favoritos
    fun addFavorite(bookItem: BookItem) {
        viewModelScope.launch {
            repository.insert(bookItem.toEntity())
        }
    }

    // Función para eliminar de favoritos
    fun removeFavorite(bookItem: BookItem) {
        viewModelScope.launch {
            repository.delete(bookItem.toEntity())
        }
    }

    // Función para verificar si un libro ya es favorito
    fun isFavorite(bookId: String): StateFlow<Boolean> {
        return favoriteBooks.map { list ->
            list.any { it.id == bookId }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    }

    // Extensión para convertir BookItem a BookEntity
    private fun BookItem.toEntity(): BookEntity {
        return BookEntity(
            id = this.id,
            title = this.volumeInfo.title,
            authors = this.volumeInfo.authors?.joinToString(", ") ?: "Autor desconocido",
            description = this.volumeInfo.description,
            imageUrl = this.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")
        )
    }
}
