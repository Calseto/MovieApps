package com.example.movieapps.data.moviedbapi

import com.example.movieapps.data.moviedbapi.response.GenreCollection
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbService {

    @GET("genre/movie/list")
    suspend fun fetchGenreList(
        @Query("api_key") apiKey: String = "66855d97b40d5c979c158598e4900477"
    ): Response<GenreCollection>
}