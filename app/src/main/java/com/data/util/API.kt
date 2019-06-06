package com.data.util

import com.data.model.Film
import com.data.model.Result
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import timber.log.Timber

interface API {

    @GET("movie/popular")
    fun getFilms(
        @Query("api_key") token: String
    ): Deferred<Response<Result>>

    @GET("movie/{movie_id}")
    fun getFilmById(
        @Path("movie_id") id: Int,
        @Query("api_key") token: String
    ): Deferred<Response<Film>>


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
            .baseUrl("https://api.themoviedb.org/3/")
            .build()

        fun get(): API {
            return retrofit.create(API::class.java)
        }


       /* private val retrofitImage: Retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger
                { message -> Timber.i(message) }).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://image.tmdb.org/t/p/")
            .build()

        fun getInstanceImageBaseUrl(): API {
            return retrofitImage.create(API::class.java)
        }
    }*/
}
}