package proj.kolot.com.placeholder.data.source

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import proj.kolot.com.placeholder.data.model.User
import proj.kolot.com.placeholder.data.source.db.UserDao
import ua.kolot.test.data.Result
import javax.inject.Inject


open class Repository @Inject constructor(val dataSource: DataSource, val userDao: UserDao) {

    @WorkerThread
    suspend fun load(): Result<List<User>> {
        var users = userDao.getAll()
        if (users.size == 0) {
            val result = dataSource.users()
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
    suspend fun getAllUsers(): LiveData<List<User>> {
        return userDao.getAllUsers()
    }

    @WorkerThread
    suspend fun getById(id: Int): User {
        return userDao.load(id)
    }
}

