package com.example.android_project_onwe.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.android_project_onwe.model.User

class RegistrationViewModel : ViewModel() {
    var user = mutableStateOf<User?>(null)
    var errorMessage = mutableStateOf<String?>(null)
    var registrationSuccess = mutableStateOf(false)

    fun registerUser(name: String, email: String, phone: String) {
        if (name.isBlank() || email.isBlank() || phone.isBlank()) {
            errorMessage.value = "Please fill in all fields"
            registrationSuccess.value = false
        } else if (!email.contains("@")) {
            errorMessage.value = "Please enter a valid email"
            registrationSuccess.value = false
        } else {
            user.value = User(name, email, phone)
            errorMessage.value = null
            registrationSuccess.value = true
        }
    }
}
