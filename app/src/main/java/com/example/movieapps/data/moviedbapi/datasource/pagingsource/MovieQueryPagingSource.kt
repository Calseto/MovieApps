package com.example.movieapps.data.moviedbapi.datasource.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapps.data.moviedbapi.MovieDbService
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.MovieQueryItem
import retrofit2.HttpException
import java.io.IOException

class MovieQueryPagingSource (
    private val apiService: MovieDbService,
    private val query: String
) : PagingSource<Int, MovieQueryItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieQueryItem> {
        val nextPageNumber = params.key ?: 1
        try {
            val response = apiService.getSearchMovie(
                query = query,
                page = nextPageNumber
            )
            val movieItems = response.body()?.results?.filterNotNull() ?: emptyList()
            return LoadResult.Page(
                data = movieItems,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response.body()?.results.isNullOrEmpty()) null else nextPageNumber + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieQueryItem>): Int? {
        return null
    }
}