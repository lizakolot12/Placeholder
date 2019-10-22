package proj.kolot.com.placeholder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import proj.kolot.com.placeholder.data.Result
import proj.kolot.com.placeholder.data.model.LoggedUser
import proj.kolot.com.placeholder.data.model.User
import proj.kolot.com.placeholder.data.source.Repository
import proj.kolot.com.placeholder.data.source.db.UserDao
import proj.kolot.com.placeholder.data.source.local.LocalSource
import proj.kolot.com.placeholder.data.source.remote.RemoteDataSource

class RepositoryTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    private val testModelScope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined)

    private lateinit var repository: Repository
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localSource: LocalSource
    private lateinit var userDao: UserDao

    @Before
    fun setupViewModel() {
        remoteDataSource = mock(RemoteDataSource::class.java)
        localSource = mock(LocalSource::class.java)
        userDao = mock(UserDao::class.java)
        repository = Repository(remoteDataSource, userDao, localSource)
    }

    @Test
    fun load_notLoggedUser() {
        var result: Result<List<User>>? = null
        doReturn(LoggedUser("", "", "", "")).`when`(localSource).getLoggedUser()
        ////
        testModelScope.launch(Dispatchers.Unconfined) {
            result = repository.load()
        }
        ///
        verify(localSource, times(1)).getLoggedUser()
        assertEquals("Error(exception=java.lang.Exception: Authorisation error)", result.toString())

    }

}