package com.ui.view

import com.ui.activity.ViewFilmActivity
import java.net.URL

interface ViewFilmView {
    var activity: ViewFilmActivity

    fun setupView()
    fun setupAdapter()
    fun setupLayoutManager()
     fun setupActionBar(title: String)

    fun setupTitle(title: String)
    fun setupBudget(budget: Int)
    fun setupOverview(overview: String)
    fun setupPopularity(popularity: String)
    fun setupAdult(adult: Boolean)
    fun setupReleaseDate(releaseDate: String)
    fun setupURL(homepage: String)
}