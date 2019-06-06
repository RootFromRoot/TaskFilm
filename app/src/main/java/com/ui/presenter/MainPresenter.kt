package com.ui.presenter

import android.content.Intent
import com.data.model.Film
import com.data.repository.FilmListRepository
import com.ui.activity.ViewFilmActivity
import com.ui.view.MainView

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