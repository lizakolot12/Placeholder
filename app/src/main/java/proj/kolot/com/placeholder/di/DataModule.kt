package proj.kolot.com.placeholder.di

import android.content.Context
import dagger.Module
import dagger.Provides
import proj.kolot.com.placeholder.data.source.local.CredentialStorage
import proj.kolot.com.placeholder.data.source.remote.RemoteDataSource
import proj.kolot.com.placeholder.data.source.Repository
import proj.kolot.com.placeholder.data.source.db.UserDao
import proj.kolot.com.placeholder.data.source.db.UsersDatabase
import proj.kolot.com.placeholder.data.source.local.LocalSource
import proj.kolot.com.placeholder.data.source.remote.UsersServices
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun localSource(ctx: Context): LocalSource {
        return CredentialStorage(ctx)
    }

    @Singleton
    @Provides
    fun apiServices(): UsersServices {
        return UsersServices.create()
    }

    @Singleton
    @Provides
    fun dataSource(apiServices: UsersServices): RemoteDataSource {
        return RemoteDataSource(apiServices)
    }

    @Singleton
    @Provides
    fun usersDao(context: Context): UserDao {
        return UsersDatabase.getDatabase(context).userDao()
    }

    @Singleton
    @Provides
    fun repository(
        remoteDataSource: RemoteDataSource,
        userDao: UserDao,
        localSource: LocalSource
    ): Repository {
        return Repository(remoteDataSource, userDao, localSource)
    }
}