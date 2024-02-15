package com.example.movieapps.data.moviedbapi.usecase

import androidx.paging.PagingData
import com.example.movieapps.data.moviedbapi.repo.ListMovieRepo
import com.example.movieapps.data.moviedbapi.response.ReviewItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(private val repo: ListMovieRepo) {
    suspend fun getMovieReviews(movieId:Int): Flow<PagingData<ReviewItem>>
            = repo.getMoviewReviews(movieId)
}