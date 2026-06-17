package com.example.biblioapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.example.biblioapp.data.BookItem
import com.example.biblioapp.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import com.example.biblioapp.data.SearchFilter

class SearchViewModel : ViewModel() {

    private val _books = MutableStateFlow<List<BookItem>>(emptyList())
    val books: StateFlow<List<BookItem>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Estado para el filtro seleccionado, inicializado en General
    private val _selectedFilter = MutableStateFlow<SearchFilter>(SearchFilter.General)
    val selectedFilter: StateFlow<SearchFilter> = _selectedFilter

    fun onFilterChanged(filter: SearchFilter) {
        _selectedFilter.value = filter
    }

    fun search(queryText: String) {
        if (queryText.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                // Construcción de la query concatenando el prefijo del filtro
                val query = "${_selectedFilter.value.prefix}$queryText"
                Log.d("DEBUG_APP", "ViewModel: Realizando búsqueda con filtro: $query")
                
                val response = RetrofitInstance.api.searchBooks(query, RetrofitInstance.API_KEY)
                val items = response.items ?: emptyList()
                
                _books.value = items
                
                if (items.isEmpty()) {
                    _errorMessage.value = "No se encontraron resultados para '$queryText'."
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
