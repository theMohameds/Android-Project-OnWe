package com.example.android_project_onwe

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials

class Auth0Manager(context: Context) {

    private val account = Auth0(
        context.getString(R.string.auth0_client_id),
        context.getString(R.string.auth0_domain)
    )

    fun login(context: Context, callback: (Credentials?) -> Unit) {
        WebAuthProvider.login(account)
            .withScheme("braekbrod")
            .withScope("openid profile email")
            .start(context, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    error.printStackTrace()
                    callback(null)
                }

                override fun onSuccess(result: Credentials) {
                    callback(result)
                }
            })
    }

    fun logout(context: Context, callback: () -> Unit) {
        WebAuthProvider.logout(account)
            .withScheme("braekbrod")
            .start(context, object : Callback<Void?, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {}
                override fun onSuccess(result: Void?) {
                    callback()
                }
            })
    }
}
