package com.example.movieapps.data.moviedbapi.datasource

import androidx.paging.PagingData
import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.data.moviedbapi.response.MovieDetailsResponse
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import com.example.movieapps.data.moviedbapi.response.ReviewItem
import com.example.movieapps.data.moviedbapi.response.TrailerResponse
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ListMovieDataSource {
    suspend fun getMovieGenre(): Flow<UiState<Response<GenreCollection>>>
    suspend fun getMovieListByGenreWithPaging(genreId:Int): Flow<PagingData<MovieItem>>
    suspend fun getMovieListByGenre(genreId:Int): Flow<UiState<Response<MovieListReqResponse>>>
    suspend fun getMovieDetails(movieId:Int):Flow<UiState<Response<MovieDetailsResponse>>>
    suspend fun getMoviewReviews(movieId: Int):Flow<PagingData<ReviewItem>>
    suspend fun getMovieTrailer(movieId:Int):Flow<UiState<Response<TrailerResponse>>>

}