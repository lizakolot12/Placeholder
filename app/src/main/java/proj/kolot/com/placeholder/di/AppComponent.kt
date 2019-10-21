package proj.kolot.com.placeholder.di

import dagger.Component
import proj.kolot.com.placeholder.ui.item.UserViewModel
import proj.kolot.com.placeholder.ui.list.ListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, ViewModule::class])
interface AppComponent {
    fun listViewModel(): ListViewModel
    fun userViewModel(): UserViewModel
}