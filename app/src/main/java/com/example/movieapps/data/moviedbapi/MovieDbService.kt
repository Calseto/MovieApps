package com.example.movieapps.data.moviedbapi

import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbService {

    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") apiKey: String = "66855d97b40d5c979c158598e4900477"
    ): Response<GenreCollection>

    @GET("discover/movie")
    suspend fun getMovieListByGenre(
        @Query("api_key") apiKey: String = "66855d97b40d5c979c158598e4900477",
        @Query("include_adult") includeAdult: Boolean=false,
        @Query("include_video") includeVideo: Boolean=false,
        @Query("page") page: Int=1,
        @Query("sort_by") sortBy: String?="popularity.desc",
        @Query("with_genres") genreId: Int
    ):Response<MovieListReqResponse>
}