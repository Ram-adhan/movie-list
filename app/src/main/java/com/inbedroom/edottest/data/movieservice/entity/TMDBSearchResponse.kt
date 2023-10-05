package com.inbedroom.edottest.data.movieservice.entity

import com.google.gson.annotations.SerializedName

data class TMDBSearchResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: ArrayList<TMDBResultItem> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)
