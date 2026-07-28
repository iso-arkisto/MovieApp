package com.yourname.movieapp.presentation

sealed interface MovieListUiEvent {
    data class Paginate(
        val category: String
    ) : MovieListUiEvent

    data class ChangeCategory(
        val category: String
    ) : MovieListUiEvent
}