package com.moviefinder.android.models

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String? = null
)