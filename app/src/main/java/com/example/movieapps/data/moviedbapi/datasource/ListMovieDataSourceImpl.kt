package com.example.movieapps.data.moviedbapi.datasource

import com.example.movieapps.data.moviedbapi.MovieDbService
import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Error
import javax.inject.Inject

class ListMovieDataSourceImpl @Inject constructor(
    private val service: MovieDbService
) : ListMovieDataSource {
    override suspend fun getMovieGenre(): Flow<UiState<Response<GenreCollection>>> = flow {
        UiState.Loading
        val response = service.fetchGenreList()
        if (response.isSuccessful) {
            emit(UiState.Success(response))
        } else {
            emit(UiState.Error(response.message()?:"no error message"))
        }
    }.catch {
        emit(UiState.Error(it.message ?: "unrecognized error"))
    }


}
