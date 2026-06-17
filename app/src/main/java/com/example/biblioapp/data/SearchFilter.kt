package com.example.biblioapp.data

sealed class SearchFilter(val displayName: String, val prefix: String) {
    object General : SearchFilter("Todos", "")
    object Title : SearchFilter("Título", "intitle:")
    object Author : SearchFilter("Autor", "inauthor:")

    companion object {
        val allFilters: List<SearchFilter>
            get() = listOf(General, Title, Author)
    }
}
