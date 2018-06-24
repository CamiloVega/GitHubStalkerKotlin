package cvdevelopers.githubstalker.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class User constructor(@SerializedName("login") val name: String = "",
                            @SerializedName("avatar_url") val profileURL: String = "",
                            @SerializedName("following") val numberOfFollowers: Int = 0,
                            var followingList: List<User> = ArrayList(),
                            var isOrg: Boolean = false) : Serializable
