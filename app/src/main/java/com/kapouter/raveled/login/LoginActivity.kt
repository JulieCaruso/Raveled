package com.kapouter.raveled.login

import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.builder.api.DefaultApi10a
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuth1RequestToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import com.github.scribejava.core.oauth.OAuth10aService
import com.kapouter.raveled.BuildConfig
import com.kapouter.raveled.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var service: OAuth10aService
    var requestToken: OAuth1RequestToken? = null
    var accessToken: OAuth1AccessToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        service = ServiceBuilder(BuildConfig.ACCESS_KEY)
                .apiSecret(BuildConfig.SECRET_KEY)
                .callback(BuildConfig.CALLBACK_URL)
                .build { config ->
                    OAuth10aService(object : DefaultApi10a() {
                        override fun getRequestTokenEndpoint(): String = BuildConfig.API_REQUEST_TOKEN_URL

                        override fun getAuthorizationUrl(requestToken: OAuth1RequestToken): String =
                                BuildConfig.API_AUTHORIZE_URL + "?oauth_token=" + requestToken.token

                        override fun getAccessTokenEndpoint(): String = BuildConfig.API_ACCESS_TOKEN_URL

                    }, config)
                }

        login_login_button.setOnClickListener({
            val authUrl = GetAuthUrl().execute().get()
            webview.loadUrl(authUrl)
        })

        webview.setWebViewClient(object : WebViewClient() {
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
            requestToken = service.requestToken
            return service.getAuthorizationUrl(requestToken)
        }
    }

    fun getVerifier(uri: Uri): String = uri.getQueryParameter("oauth_verifier")

    inner class GetAccessToken : AsyncTask<String, Void, Boolean>() {
        override fun doInBackground(vararg params: String?): Boolean {
            val verifier = params[0]
            accessToken = service.getAccessToken(requestToken, verifier)
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            GetUser().execute()
        }
    }

    inner class GetUser : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val request = OAuthRequest(Verb.GET, "https://api.ravelry.com/current_user.json")
            service.signRequest(accessToken, request)
            val response = service.execute(request)
            return response.body
        }

        override fun onPostExecute(result: String?) {
            Log.d("azerty", result)
        }
    }
}
