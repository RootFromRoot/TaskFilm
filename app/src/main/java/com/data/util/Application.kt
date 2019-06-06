package com.data.util

import android.arch.persistence.room.Room
import android.support.multidex.MultiDexApplication

class Application : MultiDexApplication() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, AppDatabase::class.java, "task_film.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}