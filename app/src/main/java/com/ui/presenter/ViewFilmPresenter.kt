package com.ui.presenter

import com.data.util.API
import com.data.API_KEY
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

    fun getFilmDetails()
}

class ViewFilmImpl : ViewFilmPresenter {
    override lateinit var view: ViewFilmView
    override var requestInterface = API.get()

    override fun bind(view: ViewFilmView) {
        super.bind(view)
        Timber.plant(Timber.DebugTree())
    }

    override fun getFilmDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    val response =
                        requestInterface.getFilmById(view.activity.intent.getIntExtra("id", 0), API_KEY).await()
                    if (response.isSuccessful) {
                        view.activity.setupActionBar(response.body()!!.title)
                        view.activity.adapter.setGenres(response.body()!!.genres)
                        view.activity.setupTitle(response.body()!!.title)
                        view.activity.setupPopularity(response.body()!!.popularity)
                        view.activity.setupAdult(response.body()!!.adult)
                        view.activity.setupBudget(response.body()!!.budget)
                        view.activity.setupOverview(response.body()!!.overview)
                        view.activity.setupReleaseDate(response.body()!!.release_date)
                        view.activity.setupURL(response.body()!!.homepage)
                    } else Timber.i(response.code().toString())
                } catch (e: Exception) {
                    Timber.i(e.message)
                } catch (e: Throwable) {
                    Timber.i("Ooops: Something else went wrong")
                }
            }
        }
    }
}