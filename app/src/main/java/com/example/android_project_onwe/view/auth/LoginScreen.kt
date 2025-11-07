package com.example.android_project_onwe.view.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.android_project_onwe.model.auth.LoginEvent
import com.example.android_project_onwe.model.auth.LoginUiState
import com.example.android_project_onwe.viewmodel.auth.LoginViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lint.kotlin.metadata.Visibility
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff


@Composable
fun LoginRoute(
    viewModel: LoginViewModel,
    onLoggedIn: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) onLoggedIn()
    }

    LoginScreen(
        state = uiState,
        onLogin = { viewModel.onEvent(LoginEvent.ClickLogin, context) },
        onLogout = {viewModel.onEvent(LoginEvent.ClickLogout, context) },
        onErrorConsumed = { viewModel.onEvent(LoginEvent.ErrorShown, context) }
    )
}

@Composable
fun LoginScreen(
    state: LoginUiState,
    onLogin: () -> Unit,
    onLogout: () -> Unit,
    onErrorConsumed: () -> Unit,
    onForgotPassword: () -> Unit = {},
    onSignUp: () -> Unit = {}
) {
    // local fields just for the UI mock (you can pass them to ViewModel later)
    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            onErrorConsumed()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        // background image + dark scrim
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            /*Image(
                painter = painterResource(id = R.drawable.login_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

             */
            // scrim to dim the background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.45f))
            )

            // top-left title
            Text(
                text = "Log in",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 12.dp, start = 12.dp)
            )

            // main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Email
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text("Value") }
                )

                Spacer(Modifier.height(16.dp))

                // Code (OTP)
                // Code (OTP)
                var codeVisible by remember { mutableStateOf(false) }

                Text(
                    text = "Code",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                )

                TextField(
                    value = code,
                    onValueChange = { code = it },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text("Value") },
                    visualTransformation = if (codeVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (codeVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        val description = if (codeVisible) "Hide code" else "Show code"
                        IconButton(onClick = { codeVisible = !codeVisible }) {
                            Icon(imageVector = icon, contentDescription = description)
                        }
                    }
                )


                // Forgot password
                Text(
                    text = "Forgot your password?",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 8.dp)
                        .clickable { onForgotPassword() }
                )

                Spacer(Modifier.height(22.dp))

                // Next button
                Button(
                    onClick = {
                        // for now call your existing onLogin() (Auth0 flow)
                        // later you can switch to onLogin(email, code)
                        onLogin()
                    },
                    enabled = !state.isLoading,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(48.dp)
                        .widthIn(min = 120.dp)
                ) {
                    Text("Next")
                }

                Spacer(Modifier.height(36.dp))

                // Sign up hint
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "If you dont have an account you can ",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                    Text(
                        "sign up",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                        color = Color(0xFF7FB9FF),
                        modifier = Modifier.clickable { onSignUp() }
                    )
                    Text(
                        " here.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(48.dp))
            }

            // logo at bottom center
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*Image(
                    painter = painterResource(id = R.drawable.onwe_logo),
                    contentDescription = "OnWe logo"
                )

                 */
            }

            // loading overlay
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }
}
