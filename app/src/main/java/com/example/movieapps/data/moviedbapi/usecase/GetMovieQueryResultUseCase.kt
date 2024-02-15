package com.example.movieapps.data.moviedbapi.usecase

import androidx.paging.PagingData
import com.example.movieapps.data.moviedbapi.repo.ListMovieRepo
import com.example.movieapps.data.moviedbapi.response.MovieQueryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieQueryResultUseCase @Inject constructor(private val repo: ListMovieRepo) {
    suspend fun getMovieQueryResult(query: String):
            Flow<PagingData<MovieQueryItem>> = repo.getMovieQueryResult(query)
}