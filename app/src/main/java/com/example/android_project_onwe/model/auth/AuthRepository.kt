package com.example.android_project_onwe.model.auth

import android.content.Context

data class AuthUser(
    val idToken: String?,
    val accessToken: String?,
    val name:String?,
    val email:String?
)

sealed interface AuthResult {
    data object Cancelled : AuthResult
    data class Success(val user: AuthUser) : AuthResult
    data class Error (val message: String, val cause: Throwable? = null) : AuthResult
}

interface AuthRepository {
    suspend fun login(context: Context): AuthResult
    suspend fun logout(context: Context): AuthResult
    fun isAuthenticated(): Boolean
}