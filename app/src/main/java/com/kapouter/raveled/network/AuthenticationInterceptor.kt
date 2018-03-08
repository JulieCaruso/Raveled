package com.kapouter.raveled.network

import android.content.Context
import android.util.Log
import com.kapouter.raveled.App
import com.kapouter.raveled.BuildConfig
import com.koushikdutta.ion.Ion
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(val context: Context) : Interceptor {

    companion object {
        private val LOG_TAG = AuthenticationInterceptor::class.java.simpleName
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder()
                .header("Authorization", "Bearer " + App.preferencesManager.getToken())
                .build()

        val response = chain.proceed(request)

        if (response.code() == 403) {
            Log.d(LOG_TAG, "403")

            val refreshResponse = Ion.with(context)
                    .load(com.kapouter.api.BuildConfig.API_ACCESS_TOKEN_20_URL)
                    .basicAuthentication(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
                    .setBodyParameter("client_id", BuildConfig.CLIENT_ID)
                    .setBodyParameter("client_secret", BuildConfig.CLIENT_SECRET)
                    .setBodyParameter("grant_type", "refresh_token")
                    .setBodyParameter("refresh_token", App.preferencesManager.getRefreshToken())
                    .asJsonObject()
                    .withResponse()
                    .get()

            if (refreshResponse.exception != null) {
                Log.d(LOG_TAG, refreshResponse.exception.toString())
            }

            if (refreshResponse.result != null) {
                Log.d(LOG_TAG, refreshResponse.result.toString())
                App.preferencesManager.setToken(refreshResponse.result.get("access_token").asString)
                App.preferencesManager.setRefreshToken(refreshResponse.result.get("refresh_token").asString)
                App.sInstance.setUser()

                val newRequest = original.newBuilder()
                        .header("Authorization", "Bearer " + refreshResponse.result.get("access_token").asString)
                        .build()
                val newResponse = chain.proceed(newRequest)
                return newResponse
            }
        }

        return response
    }
}