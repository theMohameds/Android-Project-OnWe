package com.example.android_project_onwe.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_project_onwe.view.auth.LoginRoute

object Destinations {
    const val Login = "login"
    const val Home = "home"
}

@Composable
fun AppNavHost (
    navController: NavHostController,
    loginRoute: @Composable (onLoggedIn: () -> Unit) -> Unit,
    home: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Login,
        modifier = modifier
    ) {
        composable(Destinations.Login) {
            loginRoute {
                navController.navigate(Destinations.Home) {
                    popUpTo (Destinations.Login) { inclusive = true}
                }
            }
        }
        composable(Destinations.Home) {home()}
    }
}
