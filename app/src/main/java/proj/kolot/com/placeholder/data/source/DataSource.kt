package proj.kolot.com.placeholder.data.source

import proj.kolot.com.placeholder.data.model.User
import proj.kolot.com.placeholder.data.source.remote.UsersServices
import ua.kolot.test.data.Result
import java.io.IOException
import javax.inject.Inject

class DataSource @Inject constructor(val apiService: UsersServices) {

    fun users(): Result<List<User>> {
        return try {
            val list = apiService.users().execute().body()
            Result.Success(list ?: emptyList())
        } catch (e: Throwable) {
            Result.Error(IOException("Error", e))
        }
    }
}

