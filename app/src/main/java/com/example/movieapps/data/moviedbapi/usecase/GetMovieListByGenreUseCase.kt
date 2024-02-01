package com.example.movieapps.data.moviedbapi.usecase

import com.example.movieapps.data.moviedbapi.repo.ListMovieRepo
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetMovieListByGenreUseCase @Inject constructor(
    private val repo:ListMovieRepo
) {

    suspend fun getMovieList(id:Int): Flow<UiState<Response<MovieListReqResponse>>>
    = repo.getMovieListByGenre(id)
}