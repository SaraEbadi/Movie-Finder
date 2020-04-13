package com.moviefinder.android.base

import dagger.Module
import dagger.Provides

@Module
class AppModule constructor(myApplication: MyApplication) {

    var myApplication: MyApplication

    init {
        this.myApplication = myApplication
    }

    @Provides
    fun provideMyRetroApplication(): MyApplication {
        return myApplication
    }
}