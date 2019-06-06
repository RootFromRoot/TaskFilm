package com.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.app.taskfilms.R
import com.data.model.Film
import com.data.model.Genres
import com.ui.adapter.GenresAdapter
import com.ui.presenter.ViewFilmImpl
import com.ui.presenter.ViewFilmPresenter
import com.ui.view.ViewFilmView
import kotlinx.android.synthetic.main.activity_view_film.*

class ViewFilmActivity : AppCompatActivity(), ViewFilmView {

    override var activity: ViewFilmActivity = this

    private val presenter: ViewFilmPresenter = ViewFilmImpl()

    var genres: ArrayList<Genres> = ArrayList()
    lateinit var adapter: GenresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_film)
        presenter.bind(this)
        setupAdapter()
        presenter.getFilmDetails()
    }

    @SuppressLint("SetTextI18n")
    override fun setupView(film: Film) {

        supportActionBar!!.title = film.title
        tv_film_name.text = film.title
        tv_budget.text = "Budget: ${film.budget}"
        tv_overview.text = film.overview
        tv_rate.text = "Popularity: ${film.popularity}"
        if (film.adult!!) tv_adult.text = "Adult: ${film.adult}"
        else tv_adult.text = "Adult: false"
        tv_release_date.text = "Release Date: ${film.release_date}"
        tv_homepage.text = film.homepage
    }

    override fun setupAdapter() {
        setupLayoutManager()
        adapter = GenresAdapter(genres)
        rv_genres_list.adapter = adapter
    }

    override fun setupLayoutManager() {
        rv_genres_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

}
