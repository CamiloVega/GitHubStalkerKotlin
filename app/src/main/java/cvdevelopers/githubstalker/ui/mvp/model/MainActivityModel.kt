package cvdevelopers.githubstalker.ui.mvp.model

import cvdevelopers.githubstalker.api.GithubEndpoint
import cvdevelopers.githubstalker.models.User
import io.reactivex.Observable
import javax.inject.Inject

class MainActivityModel @Inject constructor(){

    @Inject lateinit var endpoint: GithubEndpoint

    fun fetchOrganization(organization: String): Observable<List<User>> {
        return endpoint.getOrganizationMember(organization)
                .flatMapIterable { list -> list }
                .map { user -> endpoint.getUser(user.name)}
                .map { user -> user.blockingFirst() }
                .toList()
                .toObservable()
    }

    fun fetchUserFollowing(selectedUser: User): Observable<User> {
        return endpoint.getFollowingUser(selectedUser.name)
                .flatMapIterable { list -> list }
                .map { user -> endpoint.getUser(user.name)}
                .map { user -> user.blockingFirst() }
                .toList()
                .toObservable()
                .flatMap({list -> addListToUser(selectedUser, list)})

    }

    fun addListToUser(selectedUser: User, followingList: List<User>): Observable<User>{
        selectedUser.followingList = followingList
        return Observable.just(selectedUser)
    }

}