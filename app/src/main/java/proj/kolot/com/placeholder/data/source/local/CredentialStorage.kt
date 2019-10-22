package proj.kolot.com.placeholder.data.source.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import proj.kolot.com.placeholder.data.model.LoggedUser

class CredentialStorage(ctx: Context):LocalSource {
    private var sharedPreferences: SharedPreferences = ctx.getSharedPreferences(PREF_NAME, 0)
    private val _currentUser: MutableLiveData<LoggedUser> = MutableLiveData()

    init {
        _currentUser.value = getLoggedUser()
    }

    override fun getLoggedUser(): LoggedUser {
        return LoggedUser(
            sharedPreferences.getString(LOGIN_KEY, "") ?: "",
            sharedPreferences.getString(TOKEN_KEY, "") ?: "",
            sharedPreferences.getString(EMAIL_KEY, "") ?: "",
            sharedPreferences.getString(PHOTO_KEY, "") ?: ""
        )
    }

    override fun currentUser(): LiveData<LoggedUser> {
        return _currentUser
    }

    override fun saveLoggedUser(user: LoggedUser) {
        sharedPreferences.edit (commit = true){
            putString(LOGIN_KEY, user.login)
            putString(TOKEN_KEY, user.token)
            putString(EMAIL_KEY, user.email)
            putString(PHOTO_KEY, user.photoPath)
        }
        _currentUser.value = user
    }

    override fun logout() {
        saveLoggedUser(LoggedUser("", "", "", ""))
    }

    companion object {
        private const val LOGIN_KEY: String = "login_key"
        private const val TOKEN_KEY: String = "token_key"
        private const val EMAIL_KEY: String = "email_key"
        private const val PHOTO_KEY: String = "photo_key"
        private const val PREF_NAME = "users"
    }
}
