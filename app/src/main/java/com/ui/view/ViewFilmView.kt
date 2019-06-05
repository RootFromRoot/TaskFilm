package com.ui.view

import com.data.model.Film
import com.ui.activity.ViewFilmActivity

interface ViewFilmView {
    var activity: ViewFilmActivity

    fun setupView(film: Film)
    fun setupAdapter()
    fun setupLayoutManager()
}
