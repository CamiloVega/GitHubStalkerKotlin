package cvdevelopers.githubstalker

import android.app.Application
import cvdevelopers.githubstalker.dagger.ApplicationComponent
import cvdevelopers.githubstalker.dagger.ApplicationModule
import cvdevelopers.githubstalker.dagger.DaggerApplicationComponent


class GithubStalkerApplication : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this);
    }


}