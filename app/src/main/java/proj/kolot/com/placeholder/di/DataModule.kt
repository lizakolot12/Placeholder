package proj.kolot.com.placeholder.di

import android.content.Context
import dagger.Module
import dagger.Provides
import proj.kolot.com.placeholder.data.source.CredentialStorage
import proj.kolot.com.placeholder.data.source.DataSource
import proj.kolot.com.placeholder.data.source.Repository
import proj.kolot.com.placeholder.data.source.db.UserDao
import proj.kolot.com.placeholder.data.source.db.UsersDatabase
import proj.kolot.com.placeholder.data.source.remote.UsersServices
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun credentialStorage(ctx: Context): CredentialStorage {
        return CredentialStorage(ctx)
    }

    @Singleton
    @Provides
    fun apiServices(): UsersServices {
        return UsersServices.create()
    }

    @Singleton
    @Provides
    fun dataSource(apiServices: UsersServices): DataSource {
        return DataSource(apiServices)
    }

    @Singleton
    @Provides
    fun usersDao(context:Context): UserDao {
        return UsersDatabase.getDatabase(context).userDao()
    }

    @Singleton
    @Provides
    fun repository(dataSource: DataSource, userDao: UserDao): Repository {
        return Repository(dataSource, userDao)
    }
}