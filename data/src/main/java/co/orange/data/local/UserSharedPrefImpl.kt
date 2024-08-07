package co.orange.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class UserSharedPrefImpl
    @Inject
    constructor(
        private val dataStore: SharedPreferences,
    ) : UserSharedPref {
        override var accessToken: String
            get() = dataStore.getString(ACCESS_TOKEN, "").orEmpty()
            set(value) = dataStore.edit { putString(ACCESS_TOKEN, value) }

        override var refreshToken: String
            get() = dataStore.getString(REFRESH_TOKEN, "").orEmpty()
            set(value) = dataStore.edit { putString(REFRESH_TOKEN, value) }

        override var deviceToken: String
            get() = dataStore.getString(DEVICE_TOKEN, "").orEmpty()
            set(value) = dataStore.edit { putString(DEVICE_TOKEN, value) }

        override var userName: String
            get() = dataStore.getString(USER_NAME, "").orEmpty()
            set(value) = dataStore.edit { putString(USER_NAME, value) }

        override var userPhone: String
            get() = dataStore.getString(USER_PHONE, "").orEmpty()
            set(value) = dataStore.edit { putString(USER_PHONE, value) }

        override fun clearInfo() {
            dataStore.edit().clear().apply()
        }

        companion object {
            private const val ACCESS_TOKEN = "ACCESS_TOKEN"
            private const val REFRESH_TOKEN = "REFRESH_TOKEN"
            private const val DEVICE_TOKEN = "DEVICE_TOKEN"
            private const val USER_NAME = "USER_NAME"
            private const val USER_PHONE = "USER_PHONE"
        }
    }
