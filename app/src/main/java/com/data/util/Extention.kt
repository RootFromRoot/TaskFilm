package com.data.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import com.app.taskfilms.R
import com.data.model.Film

fun isConnectingToInternet(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun showErrorToast(activity: Activity, cachedFilms: ArrayList<Film>){
    if (cachedFilms.isEmpty()) Toast.makeText(
        activity,
        activity.getString(R.string.warning_internet),
        Toast.LENGTH_LONG
    ).show()
}