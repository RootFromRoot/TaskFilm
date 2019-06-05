package com.ui.presenter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.data.util.API
import com.data.API_KEY
import com.data.repository.FilmDetailsRepository
import com.data.util.Application
import com.data.util.isConnectingToInternet
import com.ui.view.ViewFilmView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

interface ViewFilmPresenter {
    var view: ViewFilmView
    var repository: FilmDetailsRepository

    fun bind(view: ViewFilmView) {
        this.view = view
    }

    fun getFilmDetails()
}

class ViewFilmImpl : ViewFilmPresenter {
    override lateinit var view: ViewFilmView
    override var repository = FilmDetailsRepository()

    override fun getFilmDetails() {
        repository.getFilmDetails(view)
    }

}