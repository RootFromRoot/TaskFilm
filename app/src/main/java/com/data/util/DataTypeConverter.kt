package com.data.util

import android.arch.persistence.room.TypeConverter
import com.data.model.Genres
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

class DateTypeConverter {
    @TypeConverter
    fun stringToGenresList(data: String?): ArrayList<Genres> {
        if (data == null)
            return ArrayList()
        return Gson().fromJson(data, object : TypeToken<ArrayList<Genres>>() {}.type)
    }

    @TypeConverter
    fun genresListToString(someObjects: ArrayList<Genres>): String {
        return Gson().toJson(someObjects)
    }
}