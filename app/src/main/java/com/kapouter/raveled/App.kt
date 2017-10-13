package com.kapouter.raveled

import android.app.Application
import android.content.Intent
import com.kapouter.api.rx.RestService
import com.kapouter.raveled.login.LoginActivity

class App : Application() {

    companion object {
        lateinit var sInstance: App
            private set

        val api by lazy {
            RestService.create()
        }
    }

    override fun onCreate() {
        super.onCreate()

        sInstance = this

        startActivity(Intent(this, LoginActivity::class.java))
    }
}