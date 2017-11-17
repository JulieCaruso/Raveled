package com.kapouter.raveled

import android.app.Application
import android.content.Context
import android.content.Intent
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.oauth.OAuth10aService
import com.kapouter.api.network.RavelryOAuthApi
import com.kapouter.api.util.PreferencesManager
import com.kapouter.raveled.login.LoginActivity
import com.kapouter.raveled.network.RestService

class App : Application() {

    companion object {
        lateinit var sInstance: App
            private set

        lateinit var preferencesManager: PreferencesManager

        val api = RestService.create()

        lateinit var oAuthService: OAuth10aService
    }

    override fun onCreate() {
        super.onCreate()

        sInstance = this

        preferencesManager = PreferencesManager(getSharedPreferences("RAVELED_PREFERENCES", Context.MODE_PRIVATE))

        App.oAuthService = ServiceBuilder(BuildConfig.ACCESS_KEY)
                .apiSecret(BuildConfig.SECRET_KEY)
                .callback(BuildConfig.CALLBACK_URL)
                .build { config ->
                    OAuth10aService(RavelryOAuthApi(), config)
                }

        if (preferencesManager.getToken().isEmpty() || preferencesManager.getTokenSecret().isEmpty()) {
            login()
        }
    }

    private fun login() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        startActivity(intent)
    }
}