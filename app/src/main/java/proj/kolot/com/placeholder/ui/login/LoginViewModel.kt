package proj.kolot.com.placeholder.ui.login

import androidx.lifecycle.ViewModel
import proj.kolot.com.placeholder.data.model.LoggedUser
import proj.kolot.com.placeholder.data.source.local.LocalSource
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val localSource: LocalSource): ViewModel() {
   fun saveUser(user:LoggedUser) {
       localSource.saveLoggedUser(user)
   }
}
