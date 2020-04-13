package com.moviefinder.android.base

import android.annotation.SuppressLint
import android.app.Application
import com.moviefinder.android.di.APIComponent
import com.moviefinder.android.di.ApiModule
import com.moviefinder.android.di.DaggerAPIComponent

@SuppressLint("Registered")
class MyApplication : Application() {
    companion object {
        var apiComponent: APIComponent = DaggerAPIComponent
            .builder()
            .apiModule(ApiModule())
            .build()
    }
}