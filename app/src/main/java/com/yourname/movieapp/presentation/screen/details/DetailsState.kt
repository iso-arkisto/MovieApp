package com.yourname.movieapp.presentation.screen.details

import com.yourname.movieapp.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
