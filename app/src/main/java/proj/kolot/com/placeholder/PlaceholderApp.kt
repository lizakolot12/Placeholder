package proj.kolot.com.placeholder

import android.app.Application
import proj.kolot.com.placeholder.di.AppComponent
import proj.kolot.com.placeholder.di.AppModule
import proj.kolot.com.placeholder.di.DaggerAppComponent

class PlaceholderApp : Application() {
    lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}