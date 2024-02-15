package com.example.movieapps.data.moviedbapi.datasource

import MoviePagingSource
import ReviewPagingSource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapps.data.moviedbapi.MovieDbService
import com.example.movieapps.data.moviedbapi.datasource.pagingsource.MovieQueryPagingSource
import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.data.moviedbapi.response.MovieDetailsResponse
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import com.example.movieapps.data.moviedbapi.response.MovieQueryItem
import com.example.movieapps.data.moviedbapi.response.ReviewItem
import com.example.movieapps.data.moviedbapi.response.TrailerResponse
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class ListMovieDataSourceImpl @Inject constructor(
    private val movieService: MovieDbService
) : ListMovieDataSource {
    override suspend fun getMovieGenre(): Flow<UiState<Response<GenreCollection>>> = flow {
        UiState.Loading
        val response = movieService.getGenreList()
        if (response.isSuccessful) {
            emit(UiState.Success(response))
        } else {
            emit(UiState.Error(response.message()?:"no error message"))
        }
    }.catch {
        emit(UiState.Error(it.message ?: "unrecognized error"))
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieListByGenreWithPaging(
        genreId: Int
    ): Flow<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieService, genreId)
            }
        ).flow
    }

    override suspend fun getMovieListByGenre(
        genreId: Int
    ): Flow<UiState<Response<MovieListReqResponse>>> = flow {
        UiState.Loading
        val response = movieService.getMovieListByGenre(genreId = genreId)
        if (response.isSuccessful) {
            emit(UiState.Success(response))
        } else {
            emit(UiState.Error(response.message()?:"no error message"))
        }
    }.catch {
        emit(UiState.Error(it.message ?: "unrecognized error"))
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieDetails(movieId: Int): Flow<UiState<Response<MovieDetailsResponse>>> = flow {
        UiState.Loading
        val response = movieService.getMovieDetails(movieId)
        if (response.isSuccessful) {
            emit(UiState.Success(response))
        } else {
            emit(UiState.Error(response.message()?:"no error message"))
        }
    }.catch {
        emit(UiState.Error(it.message ?: "unrecognized error"))
    }.flowOn(Dispatchers.IO)

    override suspend fun getMoviewReviews(
        movieId: Int
    ): Flow<PagingData<ReviewItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ReviewPagingSource(movieService, movieId)
            }
        ).flow
    }

    override suspend fun getMovieQueryResult(
        query: String
    ): Flow<PagingData<MovieQueryItem>> {
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    MovieQueryPagingSource(movieService, query)
                }
            ).flow
    }

    override suspend fun getMovieTrailer(movieId: Int): Flow<UiState<Response<TrailerResponse>>> = flow {
        UiState.Loading
        val response = movieService.getVideosById(movieId)
        if (response.isSuccessful) {
            emit(UiState.Success(response))
        } else {
            emit(UiState.Error(response.message()?:"no error message"))
        }
    }.catch {
        emit(UiState.Error(it.message ?: "unrecognized error"))
    }.flowOn(Dispatchers.IO)



}
