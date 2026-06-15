package com.example.biblioteca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.biblioteca.ui.theme.BibliotecaTheme
import com.example.biblioteca.ui_screens.SearchScreen
import com.example.biblioteca.ui_screens.FavoritesScreen
import com.example.biblioteca.ui_screens.SettingsScreen
import com.example.biblioteca.ui_screens.DetailScreen
import com.example.biblioteca.data.VolumeInfo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BibliotecaTheme {
                BibliotecaApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibliotecaApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    var selectedBook by remember { mutableStateOf<VolumeInfo?>(null) }

    // Si hay un libro seleccionado, mostramos la pantalla de detalle a pantalla completa
    if (selectedBook != null) {
        DetailScreen(
            volumeInfo = selectedBook!!,
            onBackClick = { selectedBook = null },
            onFavoriteClick = { /* Lógica de favoritos a implementar luego */ }
        )
    } else {
        // Si no hay libro seleccionado, mostramos la estructura normal con navegación
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                AppDestinations.entries.forEach {
                    item(
                        icon = {
                            Icon(
                                painterResource(it.icon),
                                contentDescription = it.label
                            )
                        },
                        label = { Text(it.label) },
                        selected = it == currentDestination,
                        onClick = { currentDestination = it }
                    )
                }
            }
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                when (currentDestination) {
                                    AppDestinations.HOME -> "Biblioteca"
                                    AppDestinations.FAVORITES -> "Favoritos"
                                    AppDestinations.SETTINGS -> "Ajustes"
                                }
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentDestination) {
                        AppDestinations.HOME ->
                            SearchScreen(onBookClick = { selectedBook = it })

                        AppDestinations.FAVORITES ->
                            FavoritesScreen()

                        AppDestinations.SETTINGS ->
                            SettingsScreen()
                    }
                }
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Inicio", R.drawable.ic_home),
    FAVORITES("Favoritos", R.drawable.ic_favorite),
    SETTINGS("Ajustes", R.drawable.ic_account_box),
}
