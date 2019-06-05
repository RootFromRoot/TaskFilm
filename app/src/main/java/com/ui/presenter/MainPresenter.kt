package com.ui.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.data.util.API
import com.data.API_KEY
import com.data.model.Film
import com.data.repository.FilmListRepository
import com.data.util.Application
import com.data.util.isConnectingToInternet
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
    var repository: FilmListRepository
    fun bind(view: MainView) {
        this.view = view
    }

    fun dispatchItemClick(film: Film)
    fun getFilmsList()
}

class MainPresenterImpl : MainPresenter {
    override lateinit var view: MainView
    override var repository = FilmListRepository()

    override fun getFilmsList() {
        repository.getFilmList(view)
    }

    override fun dispatchItemClick(film: Film) =
        view.activity.startActivity(Intent(view.activity, ViewFilmActivity::class.java).putExtra("id", film.id))
}