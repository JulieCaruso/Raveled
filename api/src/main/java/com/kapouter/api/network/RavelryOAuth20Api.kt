package com.kapouter.api.network

import com.github.scribejava.core.builder.api.DefaultApi20
import com.kapouter.api.BuildConfig

class RavelryOAuth20Api : DefaultApi20() {
    override fun getAuthorizationBaseUrl(): String = BuildConfig.API_AUTHORIZATION_20_URL

    override fun getAccessTokenEndpoint(): String = BuildConfig.API_ACCESS_TOKEN_20_URL
}