package com.example.uas_ppapba_syafiqabdillahhabib_514688

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AuthManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    private val KEY_IS_LOGGED_IN = "isLoggedin"

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }
    var userId: String?
        get() = prefs.getString(KEY_USER_ID, null)
        set(value) = prefs.edit { putString(KEY_USER_ID, value) }

    var isAdmin: Boolean
        get() = prefs.getBoolean(KEY_IS_ADMIN, false)
        set(value) = prefs.edit { putBoolean(KEY_IS_ADMIN, value) }

    fun logout() {
        prefs.edit {
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_USER_ID)
            remove(KEY_IS_ADMIN)
        }
    }

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_IS_ADMIN = "is_admin"
    }
}