package com.kapouter.api.util

import android.content.SharedPreferences

class PreferencesManager(private val preferences: SharedPreferences) {

    companion object {
        const val AUTH_TOKEN = "AUTH_TOKEN"
        const val AUTH_TOKEN_SECRET = "AUTH_TOKEN_SECRET"
    }

    fun getToken(): String {
        return preferences.getString(AUTH_TOKEN, "")
    }

    fun setToken(token: String) {
        preferences.edit()
                .putString(AUTH_TOKEN, token)
                .apply()
    }

    fun getTokenSecret(): String {
        return preferences.getString(AUTH_TOKEN_SECRET, "")
    }

    fun setTokenSecret(tokenSecret: String) {
        preferences.edit()
                .putString(AUTH_TOKEN_SECRET, tokenSecret)
                .apply()
    }
}