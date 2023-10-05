package com.inbedroom.edottest.core

import com.inbedroom.edottest.data.movieservice.MovieRepository
import okhttp3.OkHttpClient

class AppModule(private val client: OkHttpClient) {
    fun getMovieRepository(): MovieRepository = MovieRepository(client)
}