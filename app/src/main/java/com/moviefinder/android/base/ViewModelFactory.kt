package com.moviefinder.android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val networkRepository: NetworkRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchMovieViewModel::class.java) -> {
                SearchMovieViewModel(networkRepository) as T
            }
            modelClass.isAssignableFrom(DetailsMovieViewModel::class.java) -> {
                DetailsMovieViewModel(networkRepository) as T
            }
            else -> throw IllegalArgumentException("No such ViewModel class ${modelClass.name}")
        }
    }
}