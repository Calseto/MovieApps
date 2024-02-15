package com.example.movieapps.data.moviedbapi.repo

import androidx.paging.PagingData
import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.data.moviedbapi.response.MovieDetailsResponse
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import com.example.movieapps.data.moviedbapi.response.MovieQueryItem
import com.example.movieapps.data.moviedbapi.response.ReviewItem
import com.example.movieapps.data.moviedbapi.response.TrailerResponse
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ListMovieRepo {
    suspend fun getMovieGenre(): Flow<UiState<Response<GenreCollection>>>
    suspend fun getMovieListByGenre(id:Int): Flow<UiState<Response<MovieListReqResponse>>>
    suspend fun getMovieListByGenreWithPaging(id:Int): Flow<PagingData<MovieItem>>
    suspend fun getMovieDetails(movieId:Int):Flow<UiState<Response<MovieDetailsResponse>>>
    suspend fun getMoviewReviews(movieId: Int):Flow<PagingData<ReviewItem>>
    suspend fun getMovieTrailer(movieId:Int):Flow<UiState<Response<TrailerResponse>>>
    suspend fun getMovieQueryResult(query: String):Flow<PagingData<MovieQueryItem>>
}