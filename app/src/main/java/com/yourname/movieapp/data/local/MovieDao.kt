package com.yourname.movieapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yourname.movieapp.data.remote.respond.MovieListDTO

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun findMovieById(id: Int): MovieEntity?

    @Query("SELECT * FROM movies WHERE category = :category")
    suspend fun findMoviesByCategory(category: String): List<MovieEntity>
}