package com.kapouter.raveled.network

import android.content.Context
import com.kapouter.api.BuildConfig
import com.kapouter.api.model.response.PatternsResponse
import com.kapouter.api.model.response.ProjectsResponse
import com.kapouter.api.model.response.UserResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {

    companion object {
        fun create(context: Context): RestService {

            val authInterceptor = AuthenticationInterceptor(context)
            val client = OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build()

            val retrofit = Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BuildConfig.API_URL)
                    .build()
            return retrofit.create(RestService::class.java)
        }
    }

    @GET("current_user.json")
    fun getUser(): Observable<UserResponse>

    @GET("patterns/search.json")
    fun getPatterns(@Query("query") query: String?): Observable<PatternsResponse>

    @GET("projects/{username}/list.json")
    fun getProjects(@Path("username") username: String): Observable<ProjectsResponse>

}