package com.kapouter.raveled.network

import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import com.kapouter.raveled.App
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val oAuthReq = OAuthRequest(Verb.GET, "https://api.ravelry.com/current_user.json")
        val accessToken = OAuth1AccessToken(App.preferencesManager.getToken(), App.preferencesManager.getTokenSecret())
        App.oAuthService.signRequest(accessToken, oAuthReq)

        val request = original.newBuilder()
                .header("Authorization", oAuthReq.headers.get("Authorization")!!)
                .build()

        return chain.proceed(request)
    }
}