package proj.kolot.com.placeholder.data.source.local

import androidx.lifecycle.LiveData
import proj.kolot.com.placeholder.data.model.LoggedUser

interface LocalSource {

    fun getLoggedUser(): LoggedUser
    fun currentUser(): LiveData<LoggedUser>
    fun saveLoggedUser(user: LoggedUser)
    fun logout()
}
