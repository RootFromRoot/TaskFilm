package com.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.app.taskfilms.R
import com.data.model.Film
import com.data.repository.FilmRepository
import com.data.util.Application
import com.ui.adapter.FilmAdapter
import com.ui.presenter.MainPresenter
import com.ui.presenter.MainPresenterImpl
import com.ui.view.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    override var activity: MainActivity = this
    private val presenter: MainPresenter = MainPresenterImpl()

    lateinit var adapter: FilmAdapter
    var films: ArrayList<Film> = ArrayList()
   
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()

        presenter.getFilmList(this)
    }

    override fun setupView() {
        setContentView(R.layout.activity_main)
        setupAdapter()
        presenter.bind(this)
    }

    override fun setupAdapter() {
        setupLayoutManager()
        adapter = FilmAdapter(films)
        rv_film_list.adapter = adapter
        adapter.onItemClick = { presenter.dispatchItemClick(it) }
    }

    override fun setupLayoutManager() {
        rv_film_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

}
