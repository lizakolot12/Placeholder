package proj.kolot.com.placeholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import proj.kolot.com.placeholder.data.model.LoggedUser
import proj.kolot.com.placeholder.data.source.CredentialStorage
import javax.inject.Inject

class MainViewModel @Inject constructor(val credentialStorage: CredentialStorage): ViewModel() {
   fun currentUser(): LiveData<LoggedUser> {
       return credentialStorage.currentUser()
   }

    fun loggedUser():LoggedUser {
        return  credentialStorage.getLoggedUser()
    }

    fun logout(){
        credentialStorage.logout()
    }
}
