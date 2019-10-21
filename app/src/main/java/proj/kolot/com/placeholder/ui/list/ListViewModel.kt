package proj.kolot.com.placeholder.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proj.kolot.com.placeholder.data.model.User
import proj.kolot.com.placeholder.data.source.Repository
import ua.kolot.test.data.Result
import javax.inject.Inject

class ListViewModel  @Inject constructor(var repository: Repository) : ViewModel() {
    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private val _progress: MutableLiveData<Boolean> = MutableLiveData()
    private val _list: MutableLiveData<List<User>> = MutableLiveData()
    private val _error: MutableLiveData<String> = MutableLiveData()

    val progress: LiveData<Boolean> = _progress
    val list: LiveData<List<User>> = _list
    val error: LiveData<String> = _error


    fun loadList() {
        _progress.value = true
        viewModelScope.launch(Dispatchers.Default) {
            val resource = repository.load()
            _progress.postValue(false)
            if (resource is Result.Error) {
                _error.postValue(resource.toString())
            }
            if (resource is Result.Success) {
                _error.postValue(null)
                _list.postValue(resource.data)
            }
        }
    }
}