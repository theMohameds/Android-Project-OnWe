package com.example.android_project_onwe

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_project_onwe.ui.theme.AndroidProjectOnWeTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth0Manager: Auth0Manager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth0Manager = Auth0Manager(this)

        setContent {
            AndroidProjectOnWeTheme {
                LoginScreen(auth0Manager)
            }
        }
    }
}

@Composable
fun LoginScreen(auth0Manager: Auth0Manager) {
    var isLoggedIn by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoggedIn) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "You are logged in!",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(onClick = {
                        auth0Manager.logout(context) {
                            isLoggedIn = false
                            Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text("Logout")
                    }
                }
            } else {
                Button(onClick = {
                    auth0Manager.login(context) { credentials ->
                        if (credentials != null) {
                            isLoggedIn = true
                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text("Login with Auth0")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    AndroidProjectOnWeTheme {
        LoginScreen(auth0Manager = Auth0Manager(LocalContext.current))
    }
}
