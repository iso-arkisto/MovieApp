package com.yourname.movieapp.presentation.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.movieapp.domain.repository.MovieListRepository
import com.yourname.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MovieListRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    fun getMovie(
        id: Int
    ) {
        viewModelScope.launch {
            _detailsState.update { listState ->
                listState.copy(isLoading = true)
            }

            repository.getMovie(id).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _detailsState.update { listState ->
                            listState.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        _detailsState.update { listState ->
                            listState.copy(isLoading = result.loading)
                        }
                    }
                    is Resource.Success -> {
                        _detailsState.update { listState ->
                            listState.copy(
                                isLoading = false,
                                movie = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}