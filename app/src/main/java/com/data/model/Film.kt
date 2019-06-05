package com.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.net.URL

data class Result(
    val results: ArrayList<Film>
)

@Entity(tableName = "films")
data class Film(

    @PrimaryKey(autoGenerate = false)
    var id: Int? = 0,
    @ColumnInfo(name = "title")
    var title: String? = "",
    @ColumnInfo(name = "budget")
    var budget: String? = "",
    @ColumnInfo(name = "genres")
    var genres: ArrayList<Genres>? = ArrayList(),
    @ColumnInfo(name = "overview")
    var overview: String? = "",
    @ColumnInfo(name = "popularity")
    var popularity: String? = "",
    @ColumnInfo(name = "adult")
    var adult: Boolean? = false,
    @ColumnInfo(name = "release_date")
    var release_date: String? = "",
    @ColumnInfo(name = "homepage")
    var homepage: String? = ""
)

data class Genres(
    var id: Int? = 0,
    var name: String? = ""
)