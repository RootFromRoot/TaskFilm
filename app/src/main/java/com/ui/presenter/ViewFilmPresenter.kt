package com.ui.presenter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.data.util.API
import com.data.API_KEY
import com.data.util.Application
import com.ui.view.ViewFilmView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

interface ViewFilmPresenter {
    var view: ViewFilmView
    var requestInterface: API

    fun bind(view: ViewFilmView) {
        this.view = view
    }

    fun getFilmDetails(context: Context)
}

class ViewFilmImpl : ViewFilmPresenter {
    override lateinit var view: ViewFilmView
    override var requestInterface = API.get()

    override fun bind(view: ViewFilmView) {
        super.bind(view)
        Timber.plant(Timber.DebugTree())
    }

     private fun getFilmDetailsFromServer() {
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

    private fun getFilmDetailsFromDB() {
        CoroutineScope(Dispatchers.IO).launch {

            val cachedFilm = (view.activity.application as Application)
                .db
                .filmDao()
                .getById(view.activity.intent.getIntExtra("id", 0))
            CoroutineScope(Dispatchers.Main).launch {

                view.activity.setupView(cachedFilm)
            }
        }
    }

    override fun getFilmDetails(context: Context) {
        if (isConnectingToInternet(context)) getFilmDetailsFromServer()
        else getFilmDetailsFromDB()
    }

    private fun isConnectingToInternet(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        if (info != null)
            for (i in info)
                if (i.state == NetworkInfo.State.CONNECTED) return true
        return false
    }
}