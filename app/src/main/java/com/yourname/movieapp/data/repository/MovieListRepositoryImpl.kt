package com.yourname.movieapp.data.repository

import coil.network.HttpException
import com.yourname.movieapp.data.local.MovieDatabase
import com.yourname.movieapp.data.mappers.toMovie
import com.yourname.movieapp.data.mappers.toMovieEntity
import com.yourname.movieapp.data.remote.MovieApi
import com.yourname.movieapp.domain.model.Movie
import com.yourname.movieapp.domain.repository.MovieListRepository
import com.yourname.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
): MovieListRepository {



    override suspend fun getMovieList(
        category: String,
        page: Int,
        forceFetchFromRemote: Boolean
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieDatabase.movieDao.findMoviesByCategory(category)
            val shouldLoadLocalMovies = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if(shouldLoadLocalMovies) {
                emit(Resource.Success(localMovieList.map { entity ->
                    entity.toMovie(category)
                }))
                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMovieList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Network connection error"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Http error"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("App error"))
                return@flow
            }

            val movieEntities = movieListFromApi.result.let {
                it.map { moviedto ->
                    moviedto.toMovieEntity(category)
                }
            }

            movieDatabase.movieDao.upsertMovieList(movieEntities)
            emit(Resource.Success(movieEntities.map { entity ->
                entity.toMovie(category)
            }))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.findMovieById(id)

            if(movieEntity!=null) {
                emit(Resource.Success(movieEntity.toMovie(movieEntity.category)))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Error: Movie not found"))
            emit(Resource.Loading(false))
        }
    }
}