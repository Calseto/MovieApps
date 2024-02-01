package com.example.movieapps.data.moviedbapi.datasource

import androidx.paging.PagingData
import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ListMovieDataSource {
    suspend fun getMovieGenre(): Flow<UiState<Response<GenreCollection>>>
    suspend fun getMovieListByGenreWithPaging(genreId:Int): Flow<PagingData<MovieItem>>
    suspend fun getMovieListByGenre(genreId:Int): Flow<UiState<Response<MovieListReqResponse>>>
}