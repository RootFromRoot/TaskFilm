package com.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.taskfilms.R
import com.data.model.Film
import com.data.model.Genres
import com.ui.holder.FilmsViewHolder
import com.ui.holder.GenresViewHolder

class GenresAdapter(var items: ArrayList<Genres>): RecyclerView.Adapter<GenresViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        return GenresViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.genres_list_item, parent, false))
    }

    fun setGenres(genres: ArrayList<Genres>) {
        this.items = genres
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.name.text = items[position].name
    }

    override fun getItemCount(): Int {
        return items.size
    }
}