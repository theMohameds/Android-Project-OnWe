package com.example.android_project_onwe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _authEvent = MutableStateFlow("")
    val authEvent: StateFlow<String> = _authEvent

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            try {
                // Firebase create user
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _isLoggedIn.value = true
                            _authEvent.value = "Sign up successful!"
                        } else {
                            _authEvent.value = task.exception?.message ?: "Sign up failed"
                        }
                    }
            } catch (e: Exception) {
                _authEvent.value = e.message ?: "Unexpected error"
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _isLoggedIn.value = true
                            _authEvent.value = "Login successful!"
                        } else {
                            _authEvent.value = task.exception?.message ?: "Login failed"
                        }
                    }
            } catch (e: Exception) {
                _authEvent.value = e.message ?: "Unexpected error"
            }
        }
    }
}