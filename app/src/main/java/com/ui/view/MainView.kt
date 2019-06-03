package com.ui.view

import com.ui.activity.MainActivity

interface MainView{
    var  activity: MainActivity

    fun setupAdapter()
    fun setupLayoutManagers()
    fun setupView()
}