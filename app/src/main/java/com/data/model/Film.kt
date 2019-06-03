package com.data.model

data class Result(
 val results: ArrayList<Film>
)

data class Film(
    var id: Int,
    var title: String
)