package com.kapouter.api.network

import com.kapouter.api.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface RestService {

    companion object {
        fun create(): RestService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BuildConfig.API_URL)
                    .build()
            return retrofit.create(RestService::class.java)
        }
    }

}