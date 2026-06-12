package com.example.biblioteca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.biblioteca.ui.theme.BibliotecaTheme
import androidx.compose.foundation.layout.Column
import com.example.biblioteca.ui_screens.SearchScreen
import com.example.biblioteca.ui_screens.FavoritesScreen
import com.example.biblioteca.ui_screens.SettingsScreen
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

@PreviewScreenSizes
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibliotecaApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

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
        /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            /*Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
            )*/

            when(currentDestination) {
                AppDestinations.HOME ->
                    SearchScreen()

                AppDestinations.FAVORITES ->
                    FavoritesScreen()

                AppDestinations.SETTINGS ->
                    SettingsScreen()
            }
        }*/
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text("Biblioteca")
                    }
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier.padding(innerPadding)
            ) {

                when(currentDestination) {
                    AppDestinations.HOME ->
                        SearchScreen()

                    AppDestinations.FAVORITES ->
                        FavoritesScreen()

                    AppDestinations.SETTINGS ->
                        SettingsScreen()
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