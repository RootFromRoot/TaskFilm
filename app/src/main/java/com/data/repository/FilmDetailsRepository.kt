package com.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import com.data.API_KEY
import com.data.dao.FilmDao
import com.data.model.Film
import com.data.util.API
import com.data.util.Application
import com.data.util.isConnectingToInternet
import com.ui.view.ViewFilmView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

class FilmDetailsRepository {

     private val requestInterface: API = API.get()

    private fun getFilmDetailsFromServer(view: ViewFilmView) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    val response = requestInterface.getFilmById(
                        view.activity.intent.getIntExtra("id", 0), API_KEY
                    ).await()
                    if (response.isSuccessful) {
                        view.activity.setupView(response.body()!!)
                        view.activity.adapter.setGenres(response.body()!!.genres!!)
                    } else Timber.i(response.code().toString())
                } catch (e: Exception) {
                    Timber.i(e.message)
                } catch (e: Throwable) {
                    Timber.i("Ooops: Something else went wrong")
                }
            }
        }
    }

    private fun getFilmDetailsFromDB(view: ViewFilmView) {
        CoroutineScope(Dispatchers.IO).launch {

            val cachedFilm = (view.activity.application as Application)
                .db
                .filmDao()
                .getById(view.activity.intent.getIntExtra("id", 0))
            CoroutineScope(Dispatchers.Main).launch {
                view.activity.adapter.setGenres(cachedFilm.genres!!)
                view.activity.setupView(cachedFilm)
            }
        }
    }

    fun getFilmDetails(view: ViewFilmView) {
        if (isConnectingToInternet(view.activity)) getFilmDetailsFromServer(view)
        else getFilmDetailsFromDB(view)
    }
}