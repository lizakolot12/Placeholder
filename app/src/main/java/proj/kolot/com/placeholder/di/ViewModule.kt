package proj.kolot.com.placeholder.di

import dagger.Module
import dagger.Provides
import proj.kolot.com.placeholder.data.source.Repository
import proj.kolot.com.placeholder.ui.item.UserViewModel
import proj.kolot.com.placeholder.ui.list.ListViewModel

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

}