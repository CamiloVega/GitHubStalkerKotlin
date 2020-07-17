package cvdevelopers.githubstalker.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import cvdevelopers.githubstalker.R
import cvdevelopers.githubstalker.models.User
import cvdevelopers.githubstalker.ui.cells.UserViewHolder
import cvdevelopers.githubstalker.utils.image.ImageLoader
import java.util.*
import kotlin.collections.ArrayList

class UserListAdapter constructor(private var userList: List<User> = ArrayList(), private val onClickListener: ((User) -> Unit) = {}, private val imageLoader: ImageLoader? = null) : RecyclerView.Adapter<UserViewHolder>(), Filterable {

    private var filteredUserList = userList

    override fun onBindViewHolder(holder: UserViewHolder?, position: Int) {
        holder?.bindToUser(filteredUserList[position], onClickListener, imageLoader)
    }

    override fun getItemCount(): Int = filteredUserList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return UserViewHolder(layoutInflater.inflate(R.layout.user_cell_view_layout, parent, false))
    }

    fun resetFilter () {
        filteredUserList = userList
        notifyDataSetChanged()
    }

    fun filterDataWithString(filter: String) = getFilter().filter(filter)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                filteredUserList = results.values as List<User>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
                val filteredResults = filterArray(constraint)
                Collections.sort(filteredResults) { user1, user2 -> user1.name.compareTo(user2.name, true)}
                val results = Filter.FilterResults()
                results.values = filteredResults
                results.count = filteredResults.size
                return results
            }

            fun filterArray(constraint: CharSequence): ArrayList<User> {
                val filterArray = ArrayList<User>()
                userList.forEach {
                    if (it.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterArray.add(it)
                    }
                }
                return filterArray
            }
        }
    }
}
