package com.kapouter.raveled

import android.app.Application
import android.content.Context
import android.content.Intent
import com.kapouter.api.model.User
import com.kapouter.api.util.PreferencesManager
import com.kapouter.api.util.SchedulerTransformer
import com.kapouter.raveled.login.LoginActivity
import com.kapouter.raveled.main.MainActivity
import com.kapouter.raveled.network.RestService

class App : Application() {

    companion object {
        lateinit var sInstance: App
            private set

        lateinit var preferencesManager: PreferencesManager

        val api = RestService.create()

        var user: User? = null
    }

    override fun onCreate() {
        super.onCreate()

        sInstance = this

        preferencesManager = PreferencesManager(getSharedPreferences("RAVELED_PREFERENCES", Context.MODE_PRIVATE))

        if (preferencesManager.getToken().isEmpty())
            login()
        else if (user == null) {
            setUser()
            home()
        } else {
            home()
        }
    }

    private fun login() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        startActivity(intent)
    }

    fun home() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        startActivity(intent)
    }

    fun setUser() {
        api.getUser()
                .compose(SchedulerTransformer())
                .subscribe(
                        { userResponse ->
                            user = userResponse.user
                        }
                )
    }
}