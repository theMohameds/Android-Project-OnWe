package com.example.android_project_onwe

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.core.content.ContextCompat
import com.example.android_project_onwe.ui.theme.AndroidProjectOnWeTheme

// ----- NOTIFICATION IMPORTS -----
import com.example.android_project_onwe.utils.NotificationUtils
import com.example.android_project_onwe.viewmodel.NotificationViewModel
// ----- END NOTIFICATION IMPORTS -----

class MainActivity : ComponentActivity() {

    // ----- NOTIFICATION RELATED -----
    private val notificationViewModel by lazy {
        NotificationViewModel(applicationContext)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                testNotification()
            }
        }
    // ----- END NOTIFICATION RELATED -----

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ----- NOTIFICATION RELATED -----
        // Create notification channel (only once)
        NotificationUtils.createNotificationChannel(this)

        // Check/request notification permission
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                testNotification() // optional: for testing
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        // ----- END NOTIFICATION RELATED -----

        // Compose UI
        setContent {
            AndroidProjectOnWeTheme {
                AndroidProjectOnWeApp()
            }
        }
    }

    // ----- NOTIFICATION RELATED -----
    private fun testNotification() {
        // Delay by 3 seconds for testing
        Handler(Looper.getMainLooper()).postDelayed({
            notificationViewModel.triggerNotification(
                "Hello from Compose!",
                "This is a test notification from your MVVM setup."
            )
        }, 3000)
    }
    // ----- END NOTIFICATION RELATED -----
}

@PreviewScreenSizes
@Composable
fun AndroidProjectOnWeApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
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
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    PROFILE("Profile", Icons.Default.AccountBox),
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidProjectOnWeTheme {
        Greeting("Android")
    }
}
