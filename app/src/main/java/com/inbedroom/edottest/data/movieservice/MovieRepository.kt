package com.inbedroom.edottest.data.movieservice

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inbedroom.edottest.core.ErrorCode
import com.inbedroom.edottest.core.NetworkClient
import com.inbedroom.edottest.core.ResponseResult
import com.inbedroom.edottest.data.movieservice.entity.TMDBSearchResponse
import okhttp3.OkHttpClient
import org.json.JSONObject

class MovieRepository(private val client: OkHttpClient) {
    companion object {
        fun getImageUrl(lastPath: String): String {
            return """https://image.tmdb.org/t/p/w500/$lastPath"""
        }
    }
    fun getMovies(): ResponseResult<TMDBSearchResponse> {
        val request = NetworkClient.getMovieDBBuilder("""search/movie?query=avengers&include_adult=false&language=en-US&page=1""")

        return try {
            val response = NetworkClient
                .getResultOrFailure(client.newCall(request).execute())
            when (response) {
                is ResponseResult.Success -> {
                    val typeToken = object : TypeToken<TMDBSearchResponse>() {}.type
                    val value = Gson().fromJson<TMDBSearchResponse>(response.data, typeToken)
                    val data = value.results.map {
                        it.copy(posterPath = it.posterPath?.let { path -> getImageUrl(path) })
                    }.toCollection(ArrayList())
                    ResponseResult.Success(data = value.copy(results = data))
                }
                is ResponseResult.Error -> {
                    val json = JSONObject(response.message)
                    ResponseResult.Error(message = json.optString("status_message"), code = json.optInt("status_code", ErrorCode.UNKNOWN))
                }
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", "getMovies: $e", )
            ResponseResult.Error(e.localizedMessage.orEmpty())
        }

    }

    fun getSeries(): ResponseResult<TMDBSearchResponse> {
        val request = NetworkClient.getMovieDBBuilder("""search/tv?query=avengers&include_adult=false&language=en-US&page=1""")

        return try {
            val response = NetworkClient
                .getResultOrFailure(client.newCall(request).execute())
            when (response) {
                is ResponseResult.Success -> {
                    val typeToken = object : TypeToken<TMDBSearchResponse>() {}.type
                    val value = Gson().fromJson<TMDBSearchResponse>(response.data, typeToken)
                    val data = value.results.map {
                        it.copy(posterPath = it.posterPath?.let { path -> getImageUrl(path) })
                    }.toCollection(ArrayList())
                    ResponseResult.Success(data = value.copy(results = data))
                }
                is ResponseResult.Error -> {
                    val json = JSONObject(response.message)
                    ResponseResult.Error(message = json.optString("status_message"), code = json.optInt("status_code", ErrorCode.UNKNOWN))
                }
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", "getMovies: $e", )
            ResponseResult.Error(e.localizedMessage.orEmpty())
        }

    }
}