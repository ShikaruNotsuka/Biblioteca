package com.example.biblioteca.ui_screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.biblioteca.data.BookEntity
import com.example.biblioteca.data.BookItem
import com.example.biblioteca.data.ImageLinks
import com.example.biblioteca.data.VolumeInfo
import com.example.biblioteca.viewmodel.BookViewModel

@Composable
fun FavoritesScreen(
    viewModel: BookViewModel,
    onBookClick: (BookItem) -> Unit
) {
    val favorites by viewModel.favoriteBooks.collectAsStateWithLifecycle()

    if (favorites.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No tienes libros favoritos aún.")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favorites) { entity ->
                FavoriteBookItem(
                    entity = entity,
                    onClick = { onBookClick(entity.toBookItem()) }
                )
            }
        }
    }
}

@Composable
fun FavoriteBookItem(entity: BookEntity, onClick: () -> Unit) {
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
            AsyncImage(
                model = entity.imageUrl,
                contentDescription = "Portada de ${entity.title}",
                modifier = Modifier
                    .size(60.dp, 90.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entity.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                Text(
                    text = entity.authors,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

// Extensión para facilitar la navegación reutilizando DetailScreen
private fun BookEntity.toBookItem(): BookItem {
    return BookItem(
        id = this.id,
        volumeInfo = VolumeInfo(
            title = this.title,
            authors = this.authors.split(", "),
            description = this.description,
            imageLinks = ImageLinks(thumbnail = this.imageUrl)
        )
    )
}
