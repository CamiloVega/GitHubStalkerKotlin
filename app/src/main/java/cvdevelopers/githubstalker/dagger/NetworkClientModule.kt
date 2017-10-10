package cvdevelopers.githubstalker.dagger

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import cvdevelopers.githubstalker.api.GithubEndpoint
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkClientModule  {
    @Provides
    @Singleton
    fun provideGson() = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()



    @Provides
    @Singleton
    fun provideGithubEndpoint(gson: Gson): GithubEndpoint = Retrofit.Builder()
            .baseUrl(GithubEndpoint.SERVER)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(GithubEndpoint::class.java)
}
