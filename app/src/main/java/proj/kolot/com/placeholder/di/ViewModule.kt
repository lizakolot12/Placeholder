package proj.kolot.com.placeholder.di

import dagger.Module
import dagger.Provides
import proj.kolot.com.placeholder.MainViewModel
import proj.kolot.com.placeholder.data.source.CredentialStorage
import proj.kolot.com.placeholder.data.source.Repository
import proj.kolot.com.placeholder.ui.item.UserViewModel
import proj.kolot.com.placeholder.ui.list.ListViewModel
import proj.kolot.com.placeholder.ui.login.LoginViewModel

@Module
class ViewModule {

    @Provides
    fun listViewModule(repository: Repository): ListViewModel {
        return ListViewModel(repository)
    }

    @Provides
    fun userViewModule(repository: Repository): UserViewModel {
        return UserViewModel(repository)
    }

    @Provides
    fun loginViewModule(credentialStorage: CredentialStorage): LoginViewModel {
        return LoginViewModel(credentialStorage)
    }

    @Provides
    fun mainViewModule(credentialStorage: CredentialStorage): MainViewModel {
        return MainViewModel(credentialStorage)
    }
}