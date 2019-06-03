package com.ui.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.films_list_item.view.*

class FilmsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var name = view.tv_name!!
    var filmCard = view.card_film_holder!!
}