package cvdevelopers.githubstalker.ui.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import cvdevelopers.githubstalker.GithubStalkerApplication

import cvdevelopers.githubstalker.R
import cvdevelopers.githubstalker.utils.image.ImageLoader
import cvdevelopers.githubstalker.models.User
import cvdevelopers.githubstalker.ui.adapters.UserListAdapter
import javax.inject.Inject

class UserListFragment: Fragment() {

    interface UserListFragmentListener {
        fun onUserSelected (user: User)
        fun onFragmentBackPressed()
    }

    @Inject lateinit var imageLoader: ImageLoader

    @BindView (R.id.user_list_recycler_view)
    lateinit var recyclerView: RecyclerView

    @BindView (R.id.toolbar)
    lateinit var toolbar: Toolbar

    var completeUser: User? = null
    var userListAdapter: UserListAdapter = UserListAdapter()
    var listener: UserListFragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_user_list, container, false)
        (activity.application as GithubStalkerApplication).appComponent.inject(this)

        ButterKnife.bind(this, view)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        if (savedInstanceState != null && completeUser == null){
            completeUser = savedInstanceState.get("User") as User
        }
        val user = completeUser
        user?.let {
            recyclerView.layoutManager = LinearLayoutManager(this.activity)
            userListAdapter = UserListAdapter(user.followingList,  { user -> onUserSelected(user)}, imageLoader)
            recyclerView.adapter = userListAdapter
            toolbar.title = user.name
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(!user.isOrg)
            toolbar.setNavigationOnClickListener { listener?.onFragmentBackPressed() }
        }
        // Inflate the layout for this fragment
        return view
    }

    fun filterUsersByString(filterString: String) {
        userListAdapter.filterDataWithString(filterString)
    }

    fun onUserSelected(user: User) {
        listener?.onUserSelected(user)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is UserListFragmentListener){
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onSaveInstanceState(state: Bundle?) {
        super.onSaveInstanceState(state)
        state?.putSerializable("User", completeUser);
    }

    fun resetFilter() {userListAdapter.resetFilter()}

}
