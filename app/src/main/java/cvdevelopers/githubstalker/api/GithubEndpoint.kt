package cvdevelopers.githubstalker.api

import cvdevelopers.githubstalker.models.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubEndpoint {

    @GET ("/users/{id}")
    fun getUser (@Path("id") user: String) : Observable<User>

    @GET("/users/{id}/following")
    fun getFollowingUser(@Path("id") user: String): Observable<List<User>>

    @GET("/orgs/{id}/members")
    fun getOrganizationMember(@Path("id") organization: String) : Observable<List<User>>

    companion object {
        val SERVER = "https://api.github.com"
    }
}