package com.yourname.movieapp.domain.repository

import com.yourname.movieapp.domain.model.Movie
import com.yourname.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        category: String,
        page: Int,
        forceFetchFromRemote: Boolean
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>

}