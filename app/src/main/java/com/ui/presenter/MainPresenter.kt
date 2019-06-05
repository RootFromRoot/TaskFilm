package com.ui.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.data.util.API
import com.data.API_KEY
import com.data.model.Film
import com.data.util.Application
import com.ui.activity.ViewFilmActivity
import com.ui.view.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

interface MainPresenter {
    var view: MainView
    var requestInterface: API
    fun bind(view: MainView) {
        this.view = view
    }

    fun getFilmList(context: Context)
    fun dispatchItemClick(film: Film)
}

class MainPresenterImpl : MainPresenter {
    override lateinit var view: MainView
    override var requestInterface: API = API.get()

    override fun bind(view: MainView) {
        super.bind(view)
        Timber.plant(Timber.DebugTree())
    }

    @SuppressLint("TimberExceptionLogging")
    private fun getFilmListFromServer() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = requestInterface.getFilms(API_KEY).await()
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        view.activity.adapter.setFilms(response.body()!!.results)
                    }
                    response.body()!!.results.forEach {
                        (view.activity.application as Application).db.filmDao().insert(it)
                    }
                } else Timber.i(response.code().toString())

            } catch (e: HttpException) {
                Timber.i(e.message)
            } catch (e: Throwable) {
                Timber.i("Ooops: Something else went wrong")
            }
        }
    }

    private fun getFilmListFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val cachedFilms = ArrayList((view.activity.application as Application).db.filmDao().getAll())
            CoroutineScope(Dispatchers.Main).launch {
                view.activity.adapter.setFilms(cachedFilms)
            }
        }
    }

    override fun getFilmList(context: Context) {
        if (isConnectingToInternet(context)) getFilmListFromServer()
        else getFilmListFromDB()
    }

    private fun isConnectingToInternet(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        if (info != null)
            for (i in info)
                if (i.state == NetworkInfo.State.CONNECTED) return true
        return false
    }

    override fun dispatchItemClick(film: Film) =
        view.activity.startActivity(Intent(view.activity, ViewFilmActivity::class.java).putExtra("id", film.id))
}