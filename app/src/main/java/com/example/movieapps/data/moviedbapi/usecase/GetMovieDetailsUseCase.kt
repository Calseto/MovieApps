package com.example.movieapps.data.moviedbapi.usecase

import com.example.movieapps.data.moviedbapi.repo.ListMovieRepo
import com.example.movieapps.data.moviedbapi.response.MovieDetailsResponse
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repo: ListMovieRepo) {
    suspend fun getMovieDetails(movieId:Int): Flow<UiState<Response<MovieDetailsResponse>>>{
        return repo.getMovieDetails(movieId)
    }
}