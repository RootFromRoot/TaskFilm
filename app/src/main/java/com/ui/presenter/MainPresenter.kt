package com.ui.presenter

import android.annotation.SuppressLint
import android.content.Intent
import com.data.util.API
import com.data.API_KEY
import com.data.model.Film
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

    fun getFilmListFromServer()
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
    override fun getFilmListFromServer() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    val response = requestInterface.getFilms(API_KEY).await()
                    if (response.isSuccessful) {
                        view.activity.adapter.setFilms(response.body()!!.results)
                    } else Timber.i(response.code().toString())
                } catch (e: HttpException) {
                    Timber.i(e.message)
                } catch (e: Throwable) {
                    Timber.i("Ooops: Something else went wrong")
                }
            }
        }
    }

    override fun dispatchItemClick(film: Film) =
        view.activity.startActivity(Intent(view.activity, ViewFilmActivity::class.java).putExtra("id", film.id))

}

