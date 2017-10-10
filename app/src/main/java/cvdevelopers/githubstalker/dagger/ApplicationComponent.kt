package cvdevelopers.githubstalker.dagger

import android.app.Application
import cvdevelopers.githubstalker.ui.mvp.activities.MainActivity
import cvdevelopers.githubstalker.ui.fragments.UserListFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by CamiloVega on 10/7/17.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkClientModule::class))
interface ApplicationComponent {
    fun inject(app: Application)
    fun inject(target: MainActivity)
    fun inject(target: UserListFragment)
}