package com.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import com.data.API_KEY
import com.data.dao.FilmDao
import com.data.model.Film
import com.data.util.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

class FilmRepository(private val dao: FilmDao) {

    fun getAll(context: Context): List<Film> {
        val films: ArrayList<Film> = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    if (isConnectingToInternet(context)) {
                        val response = API.get().getFilms(API_KEY).await()
                        if (response.isSuccessful) {
                            films.addAll(response.body()!!.results)
                        } else Timber.i(response.code().toString())
                    } else {
                        films.addAll(dao.getAll())
                    }
                } catch (e: HttpException) {
                    Timber.i(e.message)
                } catch (e: Throwable) {
                    Timber.i("Ooops: Something else went wrong")
                }

            }
        }
        return films
    }

    /* fun getById(id: Int) = Observable.fromCallable { dao.getById(id) }
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())*/

    fun isConnectingToInternet(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        if (info != null)
            for (i in info)
                if (i.state == NetworkInfo.State.CONNECTED) return true
        return false
    }
}