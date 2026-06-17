package com.example.biblioapp.ui_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.biblioapp.data.BookEntity
import com.example.biblioapp.data.BookItem
import com.example.biblioapp.data.ImageLinks
import com.example.biblioapp.data.VolumeInfo
import com.example.biblioapp.viewmodel.BookViewModel

@Composable
fun FavoritesScreen(
    viewModel: BookViewModel,
    onBookClick: (BookItem) -> Unit
) {
    val favorites by viewModel.favoriteBooks.collectAsStateWithLifecycle()

    if (favorites.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "No tienes libros favoritos aún.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(favorites) { entity ->
                FavoriteBookItem(
                    entity = entity,
                    onClick = { onBookClick(entity.toBookItem()) },
                    onRemoveClick = { viewModel.removeFavorite(entity.toBookItem()) }
                )
            }
        }
    }
}

@Composable
fun FavoriteBookItem(
    entity: BookEntity,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        // Reborde ultra sutil que apenas se nota para dar estructura sin ensuciar
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = entity.imageUrl,
                contentDescription = "Portada de ${entity.title}",
                modifier = Modifier
                    .size(60.dp, 90.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entity.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        lineHeight = 20.sp
                    ),
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = entity.authors,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1
                )
            }

            IconButton(
                onClick = onRemoveClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Quitar de favoritos",
                    tint = Color.Red,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

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
