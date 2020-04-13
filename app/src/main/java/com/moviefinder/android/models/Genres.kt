package com.moviefinder.android.models

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String? = null
)