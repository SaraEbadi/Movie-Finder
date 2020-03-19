package com.moviefinder.android.features.movielist.movielistadapter

import androidx.recyclerview.widget.DiffUtil

class MovieListDiffUtils : DiffUtil.ItemCallback<ResultSearch>() {
    override fun areItemsTheSame(oldItem: ResultSearch, newItem: ResultSearch): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultSearch, newItem: ResultSearch): Boolean {
        return oldItem.originalTitle == newItem.originalTitle
    }
}