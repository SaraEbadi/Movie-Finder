package com.moviefinder.android.features.movielist.movielistadapter

import android.view.View

interface IMovieOnItemListener {
    fun onClickListener(view: View, position: Int)
}