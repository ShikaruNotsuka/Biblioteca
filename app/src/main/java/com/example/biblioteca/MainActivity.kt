package com.example.biblioteca

import android.content.IntentFilter
import android.net.ConnectivityManager
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
import com.example.biblioteca.components.NetworkChangeReceiver
import com.example.biblioteca.data.VolumeInfo
import com.example.biblioteca.data.AppDatabase
import com.example.biblioteca.data.BookRepository
import com.example.biblioteca.viewmodel.BookViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biblioteca.data.BookItem

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase
    private lateinit var repository: BookRepository
    private val networkReceiver = NetworkChangeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicialización de la base de datos y el repositorio
        database = AppDatabase.getDatabase(this)
        repository = BookRepository(database.bookDao())

        enableEdgeToEdge()
        setContent {
            var isDarkMode by rememberSaveable { mutableStateOf(false) }

            BibliotecaTheme(darkTheme = isDarkMode) {
                // Pasamos el repositorio a la App para crear el ViewModel
                BibliotecaApp(
                    repository = repository,
                    isDarkMode = isDarkMode,
                    onDarkModeChange = { isDarkMode = it }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkReceiver)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibliotecaApp(
    repository: BookRepository,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    // Inicializamos el ViewModel con una fábrica manual o usando un ViewModelProvider
    val bookViewModel: BookViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return BookViewModel(repository) as T
            }
        }
    )

    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    var selectedBook by remember { mutableStateOf<BookItem?>(null) }

    // Si hay un libro seleccionado, mostramos la pantalla de detalle a pantalla completa
    if (selectedBook != null) {
        DetailScreen(
            bookItem = selectedBook!!,
            bookViewModel = bookViewModel,
            onBackClick = { selectedBook = null }
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
                            FavoritesScreen(
                                viewModel = bookViewModel,
                                onBookClick = { selectedBook = it }
                            )

                        AppDestinations.SETTINGS ->
                            SettingsScreen(
                                isDarkMode = isDarkMode,
                                onDarkModeChange = onDarkModeChange
                            )
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
