package com.example.android_project_onwe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_project_onwe.ui.theme.AndroidProjectOnWeTheme
import com.example.android_project_onwe.view.LoginScreen
import com.example.android_project_onwe.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidProjectOnWeTheme {
                val authViewModel: AuthViewModel = viewModel()
                AndroidProjectOnWeApp(authViewModel)
            }
        }
    }
}

@Composable
fun AndroidProjectOnWeApp(authViewModel: AuthViewModel) {
    val isUserLoggedIn by authViewModel.isLoggedIn.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (!isUserLoggedIn) {
            // Show login screen if not logged in
            LoginScreen(authViewModel)
        } else {
            // Show the main app with bottom navigation
            LoggedInApp()
        }
    }
}

@Composable
fun LoggedInApp() {
    val currentDestination = rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    // Wrap in Surface to apply theme background
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    AppDestinations.entries.forEach { destination ->
                        NavigationBarItem(
                            icon = { Icon(destination.icon, contentDescription = destination.label) },
                            label = { Text(destination.label) },
                            selected = destination == currentDestination.value,
                            onClick = { currentDestination.value = destination }
                        )
                    }
                }
            }
        ) { innerPadding ->
            // Apply innerPadding inside a Column so background is correct
            Column(modifier = Modifier.padding(innerPadding)) {
                when (currentDestination.value) {
                    AppDestinations.HOME -> Greeting("Home")
                    AppDestinations.FAVORITES -> Greeting("Favorites")
                    AppDestinations.PROFILE -> Greeting("Profile")
                }
            }
        }
    }
}

enum class AppDestinations(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    PROFILE("Profile", Icons.Default.AccountBox),
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidProjectOnWeTheme {
        Greeting("Android")
    }
}
