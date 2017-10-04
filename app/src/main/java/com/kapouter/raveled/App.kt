package com.kapouter.raveled

import android.app.Application
import android.content.Intent
import com.kapouter.raveled.login.LoginActivity

class App : Application() {

    companion object {
        lateinit var sInstance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        sInstance = this
    }
}