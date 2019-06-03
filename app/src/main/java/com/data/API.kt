package com.data

import com.data.model.Result
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import timber.log.Timber

interface API {
    @GET("popular")
    fun getFilms(

        @Query("api_key") token: String
    ): Deferred<Response<Result>>



    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger
                { message -> Timber.i(message) }).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .build()

        fun get(): API {
            return retrofit.create(API::class.java)
        }
    }
}