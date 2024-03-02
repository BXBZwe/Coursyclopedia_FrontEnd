package com.example.courscyclopedia.ui.util

import android.content.Context

object SharedPreferencesUtils {

    private const val PREFERENCES_FILE_NAME = "AppPreferences"

    fun saveUserEmail(context: Context, email: String) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("userEmail", email).apply()
    }

    fun getUserEmail(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("userEmail", null)
    }

    fun clearUserEmail(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("userEmail").apply()
    }
}
