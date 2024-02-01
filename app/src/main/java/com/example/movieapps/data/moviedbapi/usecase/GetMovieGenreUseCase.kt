package com.example.movieapps.data.moviedbapi.usecase

import com.example.movieapps.data.moviedbapi.repo.ListMovieRepo
import javax.inject.Inject

class GetMovieGenreUseCase @Inject constructor(private val listMovieRepo: ListMovieRepo) {
    suspend fun getGenre()=listMovieRepo.getMovieGenre()
}