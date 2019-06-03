package com.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.taskfilms.R
import com.data.model.Film
import com.ui.holder.FilmsViewHolder

class FilmAdapter(var items: ArrayList<Film>): RecyclerView.Adapter<FilmsViewHolder>()  {

    var onItemClick: (film: Film) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        return FilmsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.films_list_item, parent, false))
    }

    fun setComments(likes: ArrayList<Film>) {
        this.items = likes
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.name.text = items[position].title
        holder.filmCard.setOnClickListener {
            onItemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}