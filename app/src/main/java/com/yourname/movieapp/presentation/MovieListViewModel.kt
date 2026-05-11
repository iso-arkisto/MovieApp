package com.yourname.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.movieapp.domain.repository.MovieListRepository
import com.yourname.movieapp.utils.Category
import com.yourname.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
): ViewModel() {
    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when(event) {
            is MovieListUiEvent.Navigate -> {
                _movieListState.update { listState ->
                    listState.copy(isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen)
                }
            }
            is MovieListUiEvent.Paginate -> {
                if(event.category == Category.POPULAR) {
                    getPopularMovieList(true)
                } else {
                    getUpcomingMovieList(true)
                }
            }
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update { listState ->
                listState.copy(isLoading = true)
            }
            movieListRepository.getMovieList(
                Category.POPULAR,
                movieListState.value.popularMovieListPage,
                forceFetchFromRemote
            ).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _movieListState.update { listState ->
                            listState.copy(isLoading = false)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { movieList ->
                            _movieListState.update { listState ->
                                listState.copy(
                                    popularMovieList = movieListState.value.popularMovieList + movieList.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage+1
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _movieListState.update { listState ->
                            listState.copy(isLoading = result.loading)
                        }
                    }
                }
            }
        }
    }
    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update { listState ->
                listState.copy(isLoading = true)
            }
            movieListRepository.getMovieList(
                Category.UPCOMING,
                movieListState.value.upcomingMovieListPage,
                forceFetchFromRemote
            ).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _movieListState.update { listState ->
                            listState.copy(isLoading = false)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { movieList ->
                            _movieListState.update { listState ->
                                listState.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList + movieList.shuffled(),
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage+1
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _movieListState.update { listState ->
                            listState.copy(isLoading = result.loading)
                        }
                    }
                }
            }
            }

    }
}