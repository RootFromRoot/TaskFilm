package com.ui.presenter

import com.data.repository.FilmDetailsRepository
import com.ui.view.ViewFilmView

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