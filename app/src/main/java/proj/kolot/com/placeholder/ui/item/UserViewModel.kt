package proj.kolot.com.placeholder.ui.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proj.kolot.com.placeholder.data.model.User
import proj.kolot.com.placeholder.data.source.Repository
import javax.inject.Inject

class UserViewModel @Inject constructor(var repository: Repository) : ViewModel() {
    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private val _progress: MutableLiveData<Boolean> = MutableLiveData()
    private val _user: MutableLiveData<User> = MutableLiveData()

    val progress: LiveData<Boolean> = _progress
    val user: LiveData<User> = _user

    fun loadUser(id:Int) {
        _progress.value = true
        viewModelScope.launch(Dispatchers.Default) {
            val user = repository.getById(id)
            _progress.postValue(false)
            _user.postValue(user)

        }
    }
}
