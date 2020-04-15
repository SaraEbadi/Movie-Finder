package com.moviefinder.android.features.movielist.movielistadapter

import androidx.recyclerview.widget.DiffUtil
import com.moviefinder.android.models.ResultSearch

class MovieListDiffUtils : DiffUtil.ItemCallback<ResultSearch>() {
    override fun areItemsTheSame(oldItem: ResultSearch, newItem: ResultSearch) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ResultSearch, newItem: ResultSearch) =
        oldItem.originalTitle == newItem.originalTitle
}