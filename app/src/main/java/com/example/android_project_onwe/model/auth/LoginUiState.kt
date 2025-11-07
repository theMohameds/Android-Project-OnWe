package com.example.android_project_onwe.model.auth

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null,
    val userDisplayName: String? = null
)

sealed interface LoginEvent {
    data object ClickLogin : LoginEvent
    data object ClickLogout : LoginEvent
    data object ErrorShown : LoginEvent
}