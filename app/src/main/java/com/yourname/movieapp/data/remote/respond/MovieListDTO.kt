package com.yourname.movieapp.data.remote.respond

data class MovieListDTO(
    val page: Int,
    val results: List<MovieDTO>?,
    val total_pages: Int,
    val total_results: Int
)
