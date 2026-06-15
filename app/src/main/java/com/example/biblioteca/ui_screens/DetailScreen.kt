package com.example.biblioteca.ui_screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.biblioteca.data.BookItem
import com.example.biblioteca.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    bookItem: BookItem,
    bookViewModel: BookViewModel,
    onBackClick: () -> Unit
) {
    val isFavorite by bookViewModel.isFavorite(bookItem.id).collectAsStateWithLifecycle()
    val volumeInfo = bookItem.volumeInfo

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Libro") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isFavorite) {
                            bookViewModel.removeFavorite(bookItem)
                        } else {
                            bookViewModel.addFavorite(bookItem)
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) "Quitar de favoritos" else "Añadir a favoritos",
                            tint = if (isFavorite) Color.Red else LocalContentColor.current
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen de Portada con conversión forzada a HTTPS
            val imageUrl = volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")
            
            AsyncImage(
                model = imageUrl,
                contentDescription = volumeInfo.title,
                modifier = Modifier
                    .height(300.dp)
                    .padding(vertical = 16.dp),
                contentScale = ContentScale.Fit
            )

            // Título
            Text(
                text = volumeInfo.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Autores
            val authorsText = volumeInfo.authors?.joinToString(", ") ?: "Autor desconocido"
            Text(
                text = authorsText,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Separador sutil
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = Color.LightGray.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sección Descripción
            Text(
                text = "Descripción",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Start
            )

            Text(
                text = volumeInfo.description ?: "No hay una descripción disponible para este libro.",
                fontSize = 15.sp,
                textAlign = TextAlign.Justify,
                lineHeight = 22.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
