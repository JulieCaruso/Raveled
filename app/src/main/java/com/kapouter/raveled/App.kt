package com.kapouter.raveled

import android.app.Application

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