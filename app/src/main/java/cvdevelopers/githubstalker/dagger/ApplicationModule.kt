package cvdevelopers.githubstalker.dagger

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideApplication() = app

    @Provides
    @Singleton
    fun provideContext() = app.applicationContext

}