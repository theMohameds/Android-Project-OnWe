package com.example.android_project_onwe.viewmodel.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_project_onwe.model.auth.AuthRepository
import com.example.android_project_onwe.model.auth.AuthResult
import com.example.android_project_onwe.model.auth.LoginEvent
import com.example.android_project_onwe.model.auth.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        LoginUiState(isLoggedIn = authRepository.isAuthenticated())
    )
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(event: LoginEvent, context: Context) {
        when (event) {
            LoginEvent.ClickLogin -> login(context)
            LoginEvent.ClickLogout -> logout(context)
            LoginEvent.ErrorShown -> _uiState.update {it.copy(error = null)}
        }
    }

    private fun login(context: Context) {
        _uiState.update { it.copy(isLoading = true, error = null)}
        viewModelScope.launch {
            when (val result = authRepository.login(context)) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            userDisplayName = result.user.name ?: "Signed in"
                        )
                    }
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                is AuthResult.Cancelled -> {
                    _uiState.update { it.copy(isLoading = false, error = "Login cancelled")}
                }
            }
        }
    }

    private fun logout (context: Context) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (authRepository.logout(context)) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, isLoggedIn = false, userDisplayName = null)
                    }
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = "Logout failed") }
                }
                is AuthResult.Cancelled -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}