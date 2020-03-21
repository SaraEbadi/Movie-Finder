package com.moviefinder.android.retrofit

import com.moviefinder.android.features.detailmovie.DetailMovieViewModel
import com.moviefinder.android.models.DetailMovieResponse
import com.moviefinder.android.models.MovieModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitMainInterface {

    @GET("search/movie")
    fun getSearchMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<MovieModel>

    @GET("movie/{movie_id}")
    fun getDetailsMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Single<DetailMovieResponse>

}