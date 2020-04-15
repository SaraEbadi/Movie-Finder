package com.moviefinder.android.repository.network

import com.moviefinder.android.models.DetailMovieResponse
import com.moviefinder.android.models.MovieModel
import com.moviefinder.android.retrofit.RetrofitMainInterface
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class NetworkRepository @Inject constructor(private val retrofitMainInterface: RetrofitMainInterface) {
    fun getMovieSearch(keySearchMovie: String, apiKey: String, page: Int): Single<MovieModel> {
        return retrofitMainInterface
            .getSearchMovie(keySearchMovie, apiKey, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDetailMovie(id: Int, apiKey: String): Single<DetailMovieResponse> {
        return retrofitMainInterface
            .getDetailsMovie(id, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}