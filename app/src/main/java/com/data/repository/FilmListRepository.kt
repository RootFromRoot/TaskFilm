package com.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.data.API_KEY
import com.data.util.API
import com.data.util.Application
import com.data.util.isConnectingToInternet
import com.ui.view.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class FilmListRepository{
    private val requestInterface: API = API.get()

    @SuppressLint("TimberExceptionLogging")
    private fun getFilmListFromServer(view:MainView) {


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = requestInterface.getFilms(API_KEY).await()
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        view.activity.adapter.setFilms(response.body()!!.results)
                    }
                    response.body()!!.results.forEach {
                        getFilmDetailsFromServer(view, it.id!!)
                    }
                } else Timber.i(response.code().toString())

            } catch (e: HttpException) {
                Timber.i(e.message)
            } catch (e: Throwable) {
                Timber.i("Ooops: Something else went wrong")
            }
        }
    }

    private fun getFilmDetailsFromServer(view:MainView, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = requestInterface.getFilmById(id, API_KEY).await()
                if (response.isSuccessful) {
                    (view.activity.application as Application).db.filmDao().insert(response.body()!!)
                } else Timber.i(response.code().toString())
            } catch (e: Exception) {
                Timber.i(e)
            } catch (e: Throwable) {
                Timber.i("Ooops: Something else went wrong")
            }
        }
    }

    private fun getFilmListFromDB(view:MainView) {
        CoroutineScope(Dispatchers.IO).launch {
            val cachedFilms = ArrayList((view.activity.application as Application).db.filmDao().getAll())
            CoroutineScope(Dispatchers.Main).launch {
                view.activity.adapter.setFilms(cachedFilms)
            }
        }
    }

     fun getFilmList(view:MainView) {
        if (isConnectingToInternet(view.activity)) getFilmListFromServer(view)
        else getFilmListFromDB(view)
    }
}