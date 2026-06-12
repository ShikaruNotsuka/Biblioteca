package com.example.biblioteca.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.example.biblioteca.data.BookItem
import com.example.biblioteca.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _books = MutableStateFlow<List<BookItem>>(emptyList())
    val books: StateFlow<List<BookItem>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun search(query: String) {
        if (query.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                Log.d("DEBUG_APP", "ViewModel: Realizando búsqueda real en Google Books para: $query")
                
                // Llamada directa y real a la API
                val response = RetrofitInstance.api.searchBooks(query, RetrofitInstance.API_KEY)
                val items = response.items ?: emptyList()
                
                _books.value = items
                
                if (items.isEmpty()) {
                    _errorMessage.value = "No se encontraron resultados para '$query'."
                }
                
            } catch (e: Exception) {
                Log.e("DEBUG_APP", "ViewModel: Error en la petición", e)
                if (e.message?.contains("429") == true) {
                    _errorMessage.value = "Error 429: Google ha bloqueado temporalmente esta red por exceso de peticiones."
                } else {
                    _errorMessage.value = "Error de conexión: ${e.message}"
                }
                _books.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
