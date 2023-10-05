package com.inbedroom.edottest.feature.list


sealed interface ApiLoading
sealed interface ApiEnd

sealed interface MovieListUiState {
    object LoadingGetMovies: MovieListUiState, ApiLoading
    object LoadingGetSeries: MovieListUiState, ApiLoading
    data class SuccessGetMovies(val data: List<Movie>): MovieListUiState, ApiEnd
    data class SuccessGetSeries(val data: List<Movie>): MovieListUiState, ApiEnd
    data class Error(val message: String, val code: Int? = null): MovieListUiState, ApiEnd
}