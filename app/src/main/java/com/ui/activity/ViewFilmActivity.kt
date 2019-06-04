package com.ui.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.app.taskfilms.R
import com.data.API_KEY
import com.data.model.Genres
import com.ui.adapter.GenresAdapter
import com.ui.presenter.ViewFilmImpl
import com.ui.presenter.ViewFilmPresenter
import com.ui.view.ViewFilmView
import kotlinx.android.synthetic.main.activity_view_film.*
import java.net.URL

class ViewFilmActivity : AppCompatActivity(), ViewFilmView {

    override var activity: ViewFilmActivity = this

    val presenter: ViewFilmPresenter = ViewFilmImpl()
    lateinit var adapter: GenresAdapter
    var genres: ArrayList<Genres> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        presenter.getFilmDetails()
    }

    override fun setupView() {
        setContentView(R.layout.activity_view_film)

        presenter.bind(this)
        setupAdapter()
    }

    override fun setupAdapter() {
        setupLayoutManager()
        adapter = GenresAdapter(genres)
        rv_genres_list.adapter = adapter
    }

    override fun setupActionBar(title: String) {
        supportActionBar!!.title = title
    }

    override fun setupLayoutManager() {
        rv_genres_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    @SuppressLint("SetTextI18n")
    override fun setupTitle(title: String) {
        tv_film_name.text = "Popularity: $title"
    }

    override fun setupBudget(budget: Int) {
        tv_budget.text = "Budget: $budget"
    }

    override fun setupOverview(overview: String) {
        tv_overview.text = overview
    }

    override fun setupPopularity(popularity: String) {
        tv_rate.text = popularity
    }

    override fun setupAdult(adult: Boolean) {
      if (adult)  tv_adult.text = "Adult: $adult"
        else tv_adult.text = "Adult: false"
    }

    override fun setupReleaseDate(releaseDate: String) {
        tv_release_date.text = "Release Date: $releaseDate"
    }

    override fun setupURL(homepage: String) {
        tv_homepage.text = homepage.toString()
    }

}
