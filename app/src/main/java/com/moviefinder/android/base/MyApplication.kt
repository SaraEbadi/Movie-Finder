package com.moviefinder.android.base

import android.annotation.SuppressLint
import android.app.Application

@SuppressLint("Registered")
class MyApplication : Application() {
    companion object {
        var apiComponent:APIComponent = DaggerAPIComponent
            .builder()
            .apiModule(ApiModule())
            .build()
    }
}