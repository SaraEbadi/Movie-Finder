package com.moviefinder.android.models

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultSearchList: List<ResultSearch> = mutableListOf(),
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)