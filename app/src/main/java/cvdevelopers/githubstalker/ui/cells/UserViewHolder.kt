package cvdevelopers.githubstalker.ui.cells

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import cvdevelopers.githubstalker.R
import cvdevelopers.githubstalker.models.User
import cvdevelopers.githubstalker.utils.image.ImageLoader


class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @BindView(R.id.profile_pic) lateinit var profilePic: ImageView
    @BindView(R.id.user_name_label) lateinit var userName: TextView
    @BindView(R.id.following_badge) lateinit var followingBadge: TextView
    lateinit var user: User
    init {
        ButterKnife.bind(this, view)

    }

    fun bindToUser(user: User, onClickListener: (User) -> Unit, imageLoader: ImageLoader?) {
        this.user = user
        userName?.text = user.name
        imageLoader?.loadCircularImage(user.profileURL, profilePic)
        if (user.numberOfFollowers > 0) {
            followingBadge.visibility = View.VISIBLE
            followingBadge?.text = user.numberOfFollowers.toString()
        } else {
            followingBadge.visibility = View.GONE
        }

        itemView.setOnClickListener {
            onClickListener(user) }
    }
}