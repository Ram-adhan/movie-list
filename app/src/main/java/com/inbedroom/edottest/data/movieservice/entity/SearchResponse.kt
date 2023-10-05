package com.inbedroom.edottest.data.movieservice.entity

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Search") var search: ArrayList<SearchItem> = arrayListOf(),
    @SerializedName("totalResults") var totalResults: String? = null,
    @SerializedName("Response") var response: String? = null
)
