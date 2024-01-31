package com.example.movieapps.data.moviedbapi

import com.example.movieapps.data.moviedbapi.repo.ListMovieRepo
import javax.inject.Inject

class GetMovieGenre @Inject constructor(private val listMovieRepo: ListMovieRepo) {
    suspend fun getGenre()=listMovieRepo.getMovieGenre()
}