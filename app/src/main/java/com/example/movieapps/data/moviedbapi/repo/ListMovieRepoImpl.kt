package com.example.movieapps.data.moviedbapi.repo

import com.example.movieapps.data.moviedbapi.datasource.ListMovieDataSource
import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ListMovieRepoImpl @Inject constructor(private val listMovieDataSource: ListMovieDataSource):ListMovieRepo {
    override suspend fun getMovieGenre(): Flow<UiState<Response<GenreCollection>>> = listMovieDataSource.getMovieGenre()
}