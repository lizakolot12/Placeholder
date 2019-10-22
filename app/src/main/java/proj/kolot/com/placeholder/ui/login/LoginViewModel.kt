package proj.kolot.com.placeholder.ui.login

import androidx.lifecycle.ViewModel
import proj.kolot.com.placeholder.data.model.LoggedUser
import proj.kolot.com.placeholder.data.source.CredentialStorage
import javax.inject.Inject

class LoginViewModel @Inject constructor(val credentialStorage: CredentialStorage): ViewModel() {
   fun saveUser(user:LoggedUser) {
       credentialStorage.saveLoggedUser(user)
   }
}
