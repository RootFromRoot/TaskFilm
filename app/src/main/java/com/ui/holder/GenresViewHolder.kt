package com.ui.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.genres_list_item.view.*

class GenresViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var name = view.tv_name!!
}