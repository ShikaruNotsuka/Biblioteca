package com.example.biblioapp.ui_screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.biblioapp.data.BookItem
import com.example.biblioapp.data.SearchFilter
import com.example.biblioapp.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    onBookClick: (BookItem) -> Unit
) {

    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val books by viewModel.books.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            isLoading = isLoading,
            onSearch = { viewModel.search(query) }
        )

        FilterDropdown(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            selectedFilter = selectedFilter,
            onFilterSelected = { viewModel.onFilterChanged(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (books.isEmpty() && !isLoading && errorMessage == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Escribe algo para buscar libros")
            }
        }

        BookList(books = books, onBookClick = onBookClick)
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    isLoading: Boolean,
    onSearch: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Buscar...") },
            singleLine = true,
            enabled = !isLoading
        )
        Button(
            onClick = {
                android.util.Log.d("DEBUG_APP", "Botón presionado. Query: '$query'")
                onSearch()
            },
            enabled = !isLoading && query.isNotBlank()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Buscar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterDropdown(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    selectedFilter: SearchFilter,
    onFilterSelected: (SearchFilter) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = selectedFilter.displayName,
            onValueChange = {},
            readOnly = true,
            label = { Text("Filtrar por") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            SearchFilter.allFilters.forEach { filter ->
                DropdownMenuItem(
                    text = { Text(filter.displayName) },
                    onClick = {
                        onFilterSelected(filter)
                        onExpandedChange(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
private fun BookList(
    books: List<BookItem>,
    onBookClick: (BookItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(books) { book ->
            BookItemCard(book = book, onClick = { onBookClick(book) })
        }
    }
}

@Composable
private fun BookItemCard(
    book: BookItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")

            AsyncImage(
                model = imageUrl,
                contentDescription = "Portada de ${book.volumeInfo.title}",
                modifier = Modifier
                    .size(80.dp, 120.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.volumeInfo.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                book.volumeInfo.authors?.let {
                    Text(
                        text = it.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}
