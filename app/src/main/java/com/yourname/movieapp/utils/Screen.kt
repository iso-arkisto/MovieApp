package com.yourname.movieapp.utils

sealed class Screen(
    val route: String
) {
    object Home: Screen("main")
    object PopularMovies: Screen("popular_movies")
    object UpcomingMovies: Screen("upcoming_movies")
    object Details: Screen("details")
}