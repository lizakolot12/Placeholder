package proj.kolot.com.placeholder.data.source.remote

import proj.kolot.com.placeholder.data.model.User
import proj.kolot.com.placeholder.data.Result
import javax.inject.Inject

open class RemoteDataSource @Inject constructor(private val apiService: UsersServices) {

    fun users(): Result<List<User>> {
        return try {
            val list = apiService.users().execute().body()
            Result.Success(list ?: emptyList())
        } catch (e: Throwable) {
            Result.Error(Exception(e))
        }
    }
}

