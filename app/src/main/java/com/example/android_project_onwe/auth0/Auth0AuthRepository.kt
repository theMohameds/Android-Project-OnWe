package com.example.android_project_onwe.auth0

import android.content.Context
import com.example.android_project_onwe.model.auth.AuthRepository
import com.example.android_project_onwe.model.auth.AuthResult
import com.example.android_project_onwe.model.auth.AuthUser
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class Auth0AuthRepository (
    private val managerFactory: (Context) -> Auth0Manager = { Auth0Manager(it) }
) : AuthRepository {
    @Volatile private var cachedUser: AuthUser? = null

    override fun isAuthenticated(): Boolean = cachedUser != null

    override suspend fun login(context: Context): AuthResult = suspendCancellableCoroutine { cont ->
        val manager = managerFactory(context)
        manager.login(context) {credentials ->
            if (credentials == null) {
                if (cont.isActive) cont.resume(AuthResult.Error("Login failed"))
            }else{
                val user = AuthUser(
                    idToken = credentials.idToken,
                    accessToken = credentials.accessToken,
                    name = null,
                    email = null
                )
                cachedUser = user
                if (cont.isActive) cont.resume(AuthResult.Success(user))
            }
        }
    }

    override suspend fun logout(context: Context): AuthResult =
        suspendCancellableCoroutine { cont ->
        val manager = managerFactory(context)
            manager.logout(context){
                cachedUser = null
                if (cont.isActive) cont.resume(
                    AuthResult.Success(AuthUser(null, null, null, null))
                )
            }
        }
}