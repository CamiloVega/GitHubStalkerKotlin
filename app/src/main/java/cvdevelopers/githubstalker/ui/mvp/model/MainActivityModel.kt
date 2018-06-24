package cvdevelopers.githubstalker.ui.mvp.model

import cvdevelopers.githubstalker.api.GithubEndpoint
import cvdevelopers.githubstalker.models.User
import io.reactivex.Single
import javax.inject.Inject

class MainActivityModel @Inject constructor(){

    @Inject lateinit var endpoint: GithubEndpoint

    fun fetchOrganization(organization: String): Single<List<User>> {
        return endpoint.getOrganizationMember(organization)
                .flattenAsObservable { it }
                .flatMapSingle { endpoint.getUser(it.name) }
                .toList()
    }

    fun fetchUserFollowing(selectedUser: User): Single<User> {
        return endpoint.getFollowingUser(selectedUser.name)
                .flattenAsObservable { it }
                .flatMapSingle { endpoint.getUser(it.name) }
                .toList()
                .flatMap({addListToUser(selectedUser, it)})
    }

    fun addListToUser(selectedUser: User, followingList: List<User>): Single<User>{
        selectedUser.followingList = followingList
        return Single.just(selectedUser)
    }

}