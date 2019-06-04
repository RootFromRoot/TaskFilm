package com.data.util

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.data.dao.FilmDao
import com.data.model.Film

@Database(entities = [Film::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}