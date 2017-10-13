package com.kapouter.api.network

import com.github.scribejava.core.builder.api.DefaultApi10a
import com.github.scribejava.core.model.OAuth1RequestToken
import com.kapouter.api.BuildConfig

class RavelryOAuthApi : DefaultApi10a() {

    override fun getRequestTokenEndpoint(): String = BuildConfig.API_REQUEST_TOKEN_URL

    override fun getAuthorizationUrl(requestToken: OAuth1RequestToken): String =
            BuildConfig.API_AUTHORIZE_URL + "?oauth_token=" + requestToken.token

    override fun getAccessTokenEndpoint(): String = BuildConfig.API_ACCESS_TOKEN_URL
}