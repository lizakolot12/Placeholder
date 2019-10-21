package proj.kolot.com.placeholder.data.source

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import proj.kolot.com.placeholder.data.model.LoggedUser

class CredentialStorage(ctx: Context) {
    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = ctx.getSharedPreferences(PREF_NAME, 0)
    }

    fun getLoggedUser(): LoggedUser {
        return LoggedUser(
            sharedPreferences.getString(LOGIN_KEY, "") ?: "",
            sharedPreferences.getString(EMAIL_KEY, "") ?: "",
            sharedPreferences.getString(PHOTO_KEY, "") ?: ""
        )
    }

    fun saveLoggedUser(user: LoggedUser) {
        sharedPreferences.edit {
            putString(LOGIN_KEY, user.login)
            putString(EMAIL_KEY, user.email)
            putString(PHOTO_KEY, user.photoPath)
        }
    }

    companion object {
        private const val LOGIN_KEY: String = "login_key"
        private const val EMAIL_KEY: String = "email_key"
        private const val PHOTO_KEY: String = "photo_key"
        private const val PREF_NAME = "users"
    }
}
