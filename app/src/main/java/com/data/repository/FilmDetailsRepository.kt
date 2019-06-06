package com.data.repository

import com.data.API_KEY
import com.data.BASE_IMAGE_PATH
import com.data.util.API
import com.data.util.Application
import com.data.util.isConnectingToInternet
import com.squareup.picasso.Picasso
import com.ui.view.ViewFilmView
import kotlinx.android.synthetic.main.activity_view_film.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

                        Picasso.get()
                            .load("$BASE_IMAGE_PATH${response.body()!!.backdrop_path}")
                            .into(view.activity.iv_poster)
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
                Picasso.get()
                    .load("$BASE_IMAGE_PATH${cachedFilm.backdrop_path}")
                    .into(view.activity.iv_poster)

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