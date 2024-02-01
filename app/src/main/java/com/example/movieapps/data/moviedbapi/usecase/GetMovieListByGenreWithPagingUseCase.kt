package com.example.movieapps.data.moviedbapi.usecase

import androidx.paging.PagingData
import com.example.movieapps.data.moviedbapi.repo.ListMovieRepo
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetMovieListByGenreWithPagingUseCase@Inject constructor(
    private val repo: ListMovieRepo
) {

    suspend fun getMovieListWithPaging(id:Int): Flow<PagingData<MovieItem>>
            = repo.getMovieListByGenreWithPaging(id)
}