package com.example.movieapps.data.moviedbapi.repo

import androidx.paging.PagingData
import com.example.movieapps.data.moviedbapi.datasource.ListMovieDataSource
import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.data.moviedbapi.response.MovieDetailsResponse
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import com.example.movieapps.data.moviedbapi.response.ReviewItem
import com.example.movieapps.data.moviedbapi.response.TrailerResponse
import com.example.movieapps.utils.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ListMovieRepoImpl @Inject constructor(private val listMovieDataSource: ListMovieDataSource):ListMovieRepo {
    override suspend fun getMovieGenre(): Flow<UiState<Response<GenreCollection>>> = listMovieDataSource.getMovieGenre()
    override suspend fun getMovieListByGenre(id: Int): Flow<UiState<Response<MovieListReqResponse>>> = listMovieDataSource.getMovieListByGenre(id)
    override suspend fun getMovieListByGenreWithPaging(
        id: Int
    ): Flow<PagingData<MovieItem>> =listMovieDataSource.getMovieListByGenreWithPaging(genreId = id)

    override suspend fun getMovieDetails(movieId: Int): Flow<UiState<Response<MovieDetailsResponse>>> = listMovieDataSource.getMovieDetails(movieId)
    override suspend fun getMoviewReviews(
        movieId: Int
    ): Flow<PagingData<ReviewItem>> =listMovieDataSource.getMoviewReviews(movieId = movieId)

    override suspend fun getMovieTrailer(movieId: Int): Flow<UiState<Response<TrailerResponse>>> = listMovieDataSource.getMovieTrailer(movieId)


}