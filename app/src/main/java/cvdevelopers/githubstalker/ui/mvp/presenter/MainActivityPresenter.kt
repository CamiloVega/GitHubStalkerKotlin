package cvdevelopers.githubstalker.ui.mvp.presenter

import cvdevelopers.githubstalker.ui.mvp.model.MainActivityModel
import cvdevelopers.githubstalker.models.User
import cvdevelopers.githubstalker.ui.mvp.activities.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityPresenter @Inject constructor() {

    @Inject lateinit var model: MainActivityModel

    lateinit var view: MainActivity

    fun fetchOrganization(organization: String) {
        view.showProgressDialog(MainActivity.ProgressDialogType.ORGANIZATION)
        model.fetchOrganization(organization)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    userList ->
                    run {
                        view.dismissProgressDialog()
                        view.showFragmentForOrganization(userList)
                    }
                }, {error ->
                    run{
                        view.showErrorDialog(error.message.toString())
                    }
                })

    }

    fun fetchUser(selectedUser: User) {
        view.showProgressDialog(MainActivity.ProgressDialogType.USER)
        model.fetchUserFollowing(selectedUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userData ->
                    run {
                        view.dismissProgressDialog()
                        view.showFragmentForUser(userData)
                    }}, {error ->
                        run{
                            view.showErrorDialog(error.message.toString())
                        }
                    })
    }

    fun init() {
        fetchOrganization(FIRST_ORGANIZATION)
    }

    companion object {
        val FIRST_ORGANIZATION = "bypasslane"
    }

}