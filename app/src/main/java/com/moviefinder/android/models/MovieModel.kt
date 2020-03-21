package com.moviefinder.android.models

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var resultSearchList: List<ResultSearch> = mutableListOf(),
    @SerializedName("total_results")
    var totalResults: Int,
    @SerializedName("total_pages")
    var totalPages: Int
)