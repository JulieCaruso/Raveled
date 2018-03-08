package com.kapouter.raveled.login

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kapouter.raveled.App
import com.kapouter.raveled.BuildConfig
import com.kapouter.raveled.R
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        private val LOG_TAG = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        webview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                if (url.startsWith(BuildConfig.CALLBACK_URL)) {
                    getAccessToken(getVerifier(Uri.parse(url)))
                    return true
                }
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
                if (request.url.toString().startsWith(BuildConfig.CALLBACK_URL)) {
                    getAccessToken(getVerifier(request.url))
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        val url = com.kapouter.api.BuildConfig.API_AUTHORIZATION_20_URL +
                "?client_id=" + BuildConfig.CLIENT_ID +
                "&redirect_uri=" + BuildConfig.CALLBACK_URL +
                "&state=RaveledApplication20&scope=offline&response_type=code"
        webview.loadUrl(url)
    }

    fun getVerifier(uri: Uri): String = uri.getQueryParameter("code")

    fun getAccessToken(code: String) {
        Ion.with(this)
                .load("POST", com.kapouter.api.BuildConfig.API_ACCESS_TOKEN_20_URL)
                .basicAuthentication(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
                .setBodyParameter("client_id", BuildConfig.CLIENT_ID)
                .setBodyParameter("client_secret", BuildConfig.CLIENT_SECRET)
                .setBodyParameter("redirect_uri", BuildConfig.CALLBACK_URL)
                .setBodyParameter("grant_type", "authorization_code")
                .setBodyParameter("code", code)
                .asJsonObject()
                .setCallback { e, result ->
                    if (e != null)
                        Log.d(LOG_TAG, e.toString());

                    if (result != null) {
                        Log.d(LOG_TAG, result.toString())
                        App.preferencesManager.setToken(result.get("access_token").asString)
                        App.preferencesManager.setRefreshToken(result.get("refresh_token").asString)
                        App.sInstance.setUser()
                        App.sInstance.home()
                    }
                }
    }
}