package com.yourname.movieapp.data.mappers

import com.yourname.movieapp.data.local.MovieEntity
import com.yourname.movieapp.data.remote.respond.MovieDTO
import com.yourname.movieapp.domain.model.Movie
import kotlin.collections.joinToString

fun MovieDTO.toMovieEntity(category: String): MovieEntity {
    return MovieEntity(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        overview = overview ?: "",
        poster_path = poster_path ?: "",
        release_date = release_date ?: "",
        title = title ?: "",
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        id = id ?: -1,
        original_title = original_title ?: "",
        video = video ?: false,

        category = category,

        genre_ids = try {
            genre_ids?.joinToString(",") ?: "-1, -2"
        } catch (e: Exception){
            "-1, -2"
        }
    )
}

fun MovieEntity.toMovie(category: String): Movie {
    return Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        original_language = original_language,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        popularity = popularity,
        vote_count = vote_count,
        id = id,
        original_title = original_title,
        video = video,

        category = category,

        genre_ids = try {
            genre_ids.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1,-2)
        }
    )
}