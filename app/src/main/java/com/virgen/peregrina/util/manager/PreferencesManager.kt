package com.virgen.peregrina.util.manager

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val PREFERENCES_NAME_PROD = "serviforms_preferences"
        private const val PREFERENCES_NAME_DEV = "datarutas_preferences_debug"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_UUID = "uuid"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
        private const val KEY_USER_DATA = "user_data"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME_PROD, 0)
    private val preferencesEditor = sharedPreferences.edit()

    private fun putString(key: String, value: String) {
        with(preferencesEditor) {
            putString(key, value)
            commit()
        }
    }

    private fun putInt(key: String, value: Int) {
        with(preferencesEditor) {
            putInt(key, value)
            commit()
        }
    }

    private fun putLong(key: String, value: Long) {
        with(preferencesEditor) {
            putLong(key, value)
            commit()
        }
    }

    private fun putBoolean(key: String, value: Boolean) {
        with(preferencesEditor) {
            putBoolean(key, value)
            commit()
        }
    }

    var authToken: String
        get() = sharedPreferences.getString(KEY_AUTH_TOKEN, "") ?: ""
        set(value) = putString(KEY_AUTH_TOKEN, value ?: "")

    var email: String?
        get() = sharedPreferences.getString(KEY_EMAIL, "") ?: ""
        set(value) = putString(KEY_EMAIL, value ?: "")

    var password: String?
        get() = sharedPreferences.getString(KEY_PASSWORD, "") ?: ""
        set(value) = putString(KEY_PASSWORD, value ?: "")

    var uuid: String?
        get() = sharedPreferences.getString(KEY_UUID, "") ?: ""
        set(value) = putString(KEY_UUID, value ?: "")

    var userData: String?
        get() = sharedPreferences.getString(KEY_USER_DATA, "") ?: ""
        set(value) = putString(KEY_USER_DATA, "")


}