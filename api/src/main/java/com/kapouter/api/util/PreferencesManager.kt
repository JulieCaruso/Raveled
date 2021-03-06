package com.kapouter.api.util

import android.content.SharedPreferences

class PreferencesManager(private val preferences: SharedPreferences) {

    companion object {
        const val AUTH_TOKEN = "AUTH_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }

    fun getToken(): String = preferences.getString(AUTH_TOKEN, "")

    fun setToken(token: String) {
        preferences.edit()
                .putString(AUTH_TOKEN, token)
                .apply()
    }

    fun getRefreshToken(): String = preferences.getString(REFRESH_TOKEN, "")

    fun setRefreshToken(token: String) {
        preferences.edit()
                .putString(REFRESH_TOKEN, token)
                .apply()
    }
}