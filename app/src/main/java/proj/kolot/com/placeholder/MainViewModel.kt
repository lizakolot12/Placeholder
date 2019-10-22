package proj.kolot.com.placeholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import proj.kolot.com.placeholder.data.model.LoggedUser
import proj.kolot.com.placeholder.data.source.local.LocalSource
import javax.inject.Inject

class MainViewModel @Inject constructor(private val localSource: LocalSource) : ViewModel() {
    fun currentUser(): LiveData<LoggedUser> {
        return localSource.currentUser()
    }

    fun loggedUser(): LoggedUser {
        return localSource.getLoggedUser()
    }

    fun logout() {
        localSource.logout()
    }
}
