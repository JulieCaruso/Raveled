package com.kapouter.raveled.login

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.scribejava.core.model.OAuth1RequestToken
import com.kapouter.raveled.App
import com.kapouter.raveled.BuildConfig
import com.kapouter.raveled.R
import com.kapouter.raveled.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var requestToken: OAuth1RequestToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_login_button.setOnClickListener({
            val authUrl = GetAuthUrl().execute().get()
            webview.loadUrl(authUrl)
        })

        webview.setWebViewClient(object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                if (url.startsWith(BuildConfig.CALLBACK_URL)) {
                    GetAccessToken().execute(getVerifier(Uri.parse(url)))
                    return true
                }
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
                if (request.url.toString().startsWith(BuildConfig.CALLBACK_URL)) {
                    GetAccessToken().execute(getVerifier(request.url))
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        })
    }

    inner class GetAuthUrl : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            requestToken = App.oAuthService.requestToken
            return App.oAuthService.getAuthorizationUrl(requestToken)
        }
    }

    fun getVerifier(uri: Uri): String = uri.getQueryParameter("oauth_verifier")

    inner class GetAccessToken : AsyncTask<String, Void, Boolean>() {
        override fun doInBackground(vararg params: String?): Boolean {
            val verifier = params[0]
            val accessToken = App.oAuthService.getAccessToken(requestToken, verifier)
            App.preferencesManager.setToken(accessToken.token)
            App.preferencesManager.setTokenSecret(accessToken.tokenSecret)
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }
}
