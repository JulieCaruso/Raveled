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
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import com.kapouter.api.network.RavelryOAuth20Api
import com.kapouter.raveled.App
import com.kapouter.raveled.BuildConfig
import com.kapouter.raveled.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class OAuthActivity : AppCompatActivity() {

    lateinit var service: OAuth20Service
    var accessToken: OAuth2AccessToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        service = ServiceBuilder(BuildConfig.ACCESS_KEY)
                .apiSecret(BuildConfig.SECRET_KEY)
                .callback(BuildConfig.CALLBACK_URL)
                .scope("offline")
                .build { config -> OAuth20Service(RavelryOAuth20Api(), config) }
// TODO : https callback scheme !!


        login_login_button.setOnClickListener({
            webview.loadUrl(service.authorizationUrl)
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

        // create RestService with Authorization token this way : "Bearer " + authToken
    }

    fun getVerifier(uri: Uri): String = uri.getQueryParameter("code")

    inner class GetAccessToken : AsyncTask<String, Void, Boolean>() {
        override fun doInBackground(vararg params: String?): Boolean {
            val code = params[0]
            accessToken = service.getAccessToken(code)
            App.preferencesManager.setToken("Bearer " + accessToken!!.accessToken)
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            App.api.getUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { user ->
                                Log.d("azerty", user.user.username)
                            },
                            { e ->
                                Log.d("azerty", e.message)
                            })
        }
    }
}