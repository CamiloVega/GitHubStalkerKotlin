package cvdevelopers.githubstalker.ui.mvp.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cvdevelopers.githubstalker.GithubStalkerApplication
import cvdevelopers.githubstalker.R
import cvdevelopers.githubstalker.models.User
import androidx.fragment.app.Fragment
import androidx.core.view.MenuItemCompat
import androidx.appcompat.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import cvdevelopers.githubstalker.ui.fragments.UserListFragment
import cvdevelopers.githubstalker.ui.mvp.presenter.MainActivityPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), UserListFragment.UserListFragmentListener, SearchView.OnQueryTextListener {


    @Inject lateinit var presenter: MainActivityPresenter

    private var progressDialog: ProgressDialog? = null
    private var searchMenuItem: MenuItem? = null
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as GithubStalkerApplication).appComponent.inject(this)
        presenter.view = this
        if (savedInstanceState == null) {
            presenter.init()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        searchMenuItem = menu?.findItem(R.id.menu_search)

        searchView = MenuItemCompat.getActionView(searchMenuItem) as SearchView
        searchView?.setOnQueryTextListener(this)
        searchView?.setOnCloseListener {
            (getCurrentFragment() as UserListFragment).resetFilter()
            false
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun showFragment (fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(R.id.activity_fragment_container, fragment, getFragmentCount().toString())
                .addToBackStack(null)
                .commit()
    }

    fun showProgressDialog(dialogType: ProgressDialogType) {
        progressDialog = ProgressDialog(this)
        progressDialog?.isIndeterminate = true
        when (dialogType){
            ProgressDialogType.USER -> progressDialog?.setMessage(resources.getString(R.string.fetch_user))
            ProgressDialogType.ORGANIZATION -> progressDialog?.setMessage(resources.getString(R.string.fetch_organization))
        }
        progressDialog?.show()
    }

    fun dismissProgressDialog() = progressDialog?.dismiss()

    fun showErrorDialog(message: String) {
        dismissProgressDialog()
        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(message)
                .setCancelable(true)
                .show()
    }

    fun showFragmentForUser(completeUser: User) {
        val userListFragment = UserListFragment()
        userListFragment.completeUser = completeUser
        showFragment(userListFragment)
    }

    fun showFragmentForOrganization(userList: List<User>) {
        val userListFragment = UserListFragment()
        userListFragment.completeUser = User(followingList = userList, isOrg = true)
        showFragment(userListFragment)
    }

    override fun onUserSelected(user: User) {
       presenter.fetchUser(user)
    }

    override fun onFragmentBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1){
            supportFragmentManager.popBackStack()
        }
    }

    private fun getFragmentCount(): Int = supportFragmentManager.backStackEntryCount

    private fun getFragmentAt(index: Int): Fragment? = if (getFragmentCount() > 0) supportFragmentManager.findFragmentByTag(Integer.toString(index)) else null

    private fun getCurrentFragment(): Fragment? = getFragmentAt(getFragmentCount() - 1)

    override fun onQueryTextSubmit(query: String?): Boolean = true

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { (getCurrentFragment() as UserListFragment).filterUsersByString(it) }
        return true
    }

     enum class ProgressDialogType {
         ORGANIZATION, USER
     }
}
