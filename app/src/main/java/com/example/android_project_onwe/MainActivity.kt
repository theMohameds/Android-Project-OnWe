package com.example.android_project_onwe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.android_project_onwe.auth0.Auth0AuthRepository
import com.example.android_project_onwe.model.auth.AuthRepository
import com.example.android_project_onwe.ui.theme.AndroidProjectOnWeTheme
import com.example.android_project_onwe.view.AppNavHost
import com.example.android_project_onwe.view.HomeScreen
import com.example.android_project_onwe.view.auth.LoginRoute
import com.example.android_project_onwe.viewmodel.auth.LoginViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AndroidProjectOnWeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    // Create the repository once and hand it to the ViewModel
                    val repository: AuthRepository = remember { Auth0AuthRepository() }
                    val loginViewModel = remember { LoginViewModel(repository) }

                    AppNavHost(
                        navController = navController,
                        loginRoute = { onLoggedIn ->
                            LoginRoute(
                                viewModel = loginViewModel,
                                onLoggedIn = onLoggedIn
                            )
                        },
                        home = { HomeScreen() }
                    )
                }
            }
        }
    }
}
