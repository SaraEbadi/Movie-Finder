package com.moviefinder.android.di

import com.moviefinder.android.base.ViewModelFactory
import com.moviefinder.android.features.detailmovie.DetailMovieFragment
import com.moviefinder.android.features.movielist.MovieListFragment
import dagger.Component

@Component(modules = [ApiModule::class])
interface APIComponent {

    fun inject(mainFragment: MovieListFragment)
    fun inject(detailsFragment: DetailMovieFragment)
    fun inject(viewModelFactory: ViewModelFactory)
}