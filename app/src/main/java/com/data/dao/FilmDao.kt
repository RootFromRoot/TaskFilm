package com.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.data.model.Film

@Dao
interface FilmDao {
    @Query("SELECT * from films WHERE id =:id")
    fun getById(id: Int): Film

    @Query("SELECT * from films")
    fun getAll(): List<Film>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(film: Film)
}