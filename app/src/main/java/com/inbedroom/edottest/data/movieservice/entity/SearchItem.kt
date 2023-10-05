package com.inbedroom.edottest.data.movieservice.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchItem(
    @SerializedName("Title") var title: String? = null,
    @SerializedName("Year") var year: String? = null,
    @SerializedName("imdbID") var imdbID: String? = null,
    @SerializedName("Type") var type: String? = null,
    @SerializedName("Poster") var poster: String? = null
)
