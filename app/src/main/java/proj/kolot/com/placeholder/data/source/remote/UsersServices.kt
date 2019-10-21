package proj.kolot.com.placeholder.data.source.remote

import proj.kolot.com.placeholder.data.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface UsersServices {

    @GET("users")
    fun users(): Call<List<User>>

    companion object Factory {
        fun create(): UsersServices {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build()

            return retrofit.create(UsersServices::class.java);
        }
    }
}