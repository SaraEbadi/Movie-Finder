package com.moviefinder.android.di

import com.moviefinder.android.base.ViewModelFactory
import dagger.Component

@Component(modules = [ApiModule::class])
interface APIComponent {

    fun inject(mainFragment: MainFragment)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(viewModelFactory: ViewModelFactory)
}