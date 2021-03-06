package com.kapouter.raveled.network

import android.content.Context
import com.kapouter.api.BuildConfig
import com.kapouter.api.model.response.*
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

    // USER

    @GET("current_user.json")
    fun getUser(): Observable<UserResponse>

    @GET("projects/{username}/list.json")
    fun getProjects(@Path("username") username: String, @Query("sort") sort: String? = null): Observable<ProjectsResponse>

    @GET("people/{username}/queue/list.json")
    fun getQueue(@Path("username") username: String): Observable<QueueResponse>

    // PATTERN

    @GET("patterns/search.json")
    fun getPatterns(@Query("query") query: String? = null,
                    @Query("sort") sort: String? = null,
                    @Query("craft") craft: String? = null,
                    @Query("pc") category: String? = null,
                    @Query("meterage") meterage: String? = null,
                    @Query("colors") colors: Int? = null,
                    @Query("needle-size") needleSize: String? = null,
                    @Query("page") page: Int = 1,
                    @Query("page_size") pageSize: Int = 10): Observable<PatternsResponse>

    @GET("/patterns/{id}.json")
    fun getPattern(@Path("id") id: Int): Observable<PatternResponse>

    // YARN

    @GET("/yarns/search.json")
    fun getYarns(@Query("query") query: String?, @Query("page") page: Int = 1, @Query("page_size") pageSize: Int = 10): Observable<YarnsResponse>

}