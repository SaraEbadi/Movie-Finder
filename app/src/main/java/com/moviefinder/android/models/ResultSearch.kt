package com.moviefinder.android.models

import com.google.gson.annotations.SerializedName

data class ResultSearch(
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("overview")
    val overView: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("vote_average")
    val voteAverage: Double
)