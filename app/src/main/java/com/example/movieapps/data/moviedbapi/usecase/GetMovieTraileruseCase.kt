package com.example.movieapps.data.moviedbapi.usecase

import com.example.movieapps.data.moviedbapi.repo.ListMovieRepo
import com.example.movieapps.data.moviedbapi.response.TrailerResponse
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetMovieTraileruseCase @Inject constructor(private val repo: ListMovieRepo) {
    suspend fun getMovieTrailer(movieId:Int): Flow<UiState<Response<TrailerResponse>>> = repo.getMovieTrailer(movieId)
}