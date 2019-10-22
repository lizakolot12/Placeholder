package proj.kolot.com.placeholder.data.source

import androidx.annotation.WorkerThread
import proj.kolot.com.placeholder.data.model.User
import proj.kolot.com.placeholder.data.source.db.UserDao
import proj.kolot.com.placeholder.data.Result
import proj.kolot.com.placeholder.data.source.local.LocalSource
import proj.kolot.com.placeholder.data.source.remote.RemoteDataSource
import javax.inject.Inject

open class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val userDao: UserDao,
    private val localSource: LocalSource
) {
    @WorkerThread
    suspend fun load(): Result<List<User>> {
        if (localSource.getLoggedUser().token.isEmpty()) return Result.Error(Exception("Authorisation error"))
        var users = userDao.getAll()
        if (users.isEmpty()) {
            val result = remoteDataSource.users()
            if (result is Result.Success) {
                userDao.saveAll(result.data)
                users = result.data
                return Result.Success(users)
            } else {
                return Result.Error(Exception("error loading"))
            }
        }
        return Result.Success(users)
    }

    @WorkerThread
    suspend fun getById(id: Int): User {
        return userDao.load(id)
    }
}

