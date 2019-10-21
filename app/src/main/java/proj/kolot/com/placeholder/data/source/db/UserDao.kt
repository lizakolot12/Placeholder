package proj.kolot.com.placeholder.data.source.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import proj.kolot.com.placeholder.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun save(user: User)

    @Insert(onConflict = REPLACE)
    fun saveAll(users: List<User>)

    @Query("SELECT * FROM User WHERE id = :id LIMIT 1")
    fun load(id: Int): User

    @Query("DELETE FROM User WHERE id = :id")
    fun deleteUser(id: Int)

    @Query("SELECT * FROM User ")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM User ")
    fun getAll(): List<User>

}