package com.inbedroom.edottest.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.inbedroom.edottest.core.BaseApplication
import com.inbedroom.edottest.data.movieservice.MovieRepository
import com.inbedroom.edottest.core.ResponseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieRepository: MovieRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val movieRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BaseApplication).movieRepository
                MovieListViewModel(movieRepository)
            }
        }
    }
    private val _uiState: MutableStateFlow<MovieListUiState> =
        MutableStateFlow(MovieListUiState.LoadingGetMovies)
    val uiState: StateFlow<MovieListUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MovieListUiState.LoadingGetMovies
    )

    fun getMovies() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.emit(MovieListUiState.LoadingGetMovies)
            when (val response = movieRepository.getMovies()) {
                is ResponseResult.Success -> {
                    val result = response.data.results.map {
                        Movie(
                            image = it.posterPath.orEmpty(),
                            title = it.title.orEmpty()
                        )
                    }
                    _uiState.emit(MovieListUiState.SuccessGetMovies(result))
                }
                is ResponseResult.Error -> {
                    _uiState.emit(MovieListUiState.Error(response.message, response.code))
                }
            }
        }
    }

    fun getSeries() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.emit(MovieListUiState.LoadingGetSeries)
            when (val response = movieRepository.getSeries()) {
                is ResponseResult.Success -> {
                    val result = response.data.results.map {
                        Movie(
                            image = it.posterPath.orEmpty(),
                            title = it.title.orEmpty()
                        )
                    }
                    _uiState.emit(MovieListUiState.SuccessGetSeries(result))
                }
                is ResponseResult.Error -> {
                    _uiState.emit(MovieListUiState.Error(response.message, response.code))
                }
            }
        }
    }
}