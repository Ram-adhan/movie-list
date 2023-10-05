package com.inbedroom.edottest.core

import com.inbedroom.edottest.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class NetworkClient {
    companion object {
        const val BASE_URL = """http://10.0.2.2:3003/"""
        const val MOVIE_DB_BASE_URL = """https://api.themoviedb.org/3/"""

        fun createClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .callTimeout(20, TimeUnit.SECONDS)
                .build()
        }

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        fun getMovieDBBuilder(endpoint: String): Request {
            return Request
                .Builder()
                .url("$MOVIE_DB_BASE_URL$endpoint")
                .get()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZmRhNTZjOGYxNjEzN2U1OWU2NjliZjQ5YmU1ZDdhMSIsInN1YiI6IjVlYTFhNTQ3ZWEzN2UwMDAxYzI4OTlhYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.562m7GQzNYVtWAHJVnzXqKp85uN88JlGVgT24QxYjT0")
                .build()
        }

        fun getResultOrFailure(response: Response): ResponseResult<String> {
            return if (response.isSuccessful && response.body != null) {
                ResponseResult.Success(data = response.body!!.string())
            } else {
                ResponseResult.Error(message = response.body?.string().orEmpty())
            }.also { response.close() }
        }
    }
}