package com.moviefinder.android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviefinder.android.features.detailmovie.DetailMovieViewModel
import com.moviefinder.android.features.movielist.MovieListViewModel
import com.moviefinder.android.repository.network.NetworkRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val networkRepository: NetworkRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieListViewModel::class.java) -> {
                MovieListViewModel(networkRepository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(networkRepository) as T
            }
            else -> throw IllegalArgumentException("No such ViewModel class ${modelClass.name}")
        }
    }
}