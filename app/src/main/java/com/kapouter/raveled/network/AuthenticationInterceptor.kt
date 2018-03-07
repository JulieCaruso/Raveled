package com.kapouter.raveled.network

import com.kapouter.raveled.App
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder()
                .header("Authorization", App.preferencesManager.getToken())
                .build()

        return chain.proceed(request)
    }
}