package com.example.movieapps.data.moviedbapi.repo

import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ListMovieRepo {
    suspend fun getMovieGenre(): Flow<UiState<Response<GenreCollection>>>
}