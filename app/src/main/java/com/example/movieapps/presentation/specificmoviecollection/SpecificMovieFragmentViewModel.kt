package com.example.movieapps.presentation.specificmoviecollection

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.dompekid.base.BaseViewModel
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import com.example.movieapps.data.moviedbapi.usecase.GetMovieListByGenreUseCase
import com.example.movieapps.data.moviedbapi.usecase.GetMovieListByGenreWithPagingUseCase
import com.example.movieapps.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificMovieFragmentViewModel @Inject constructor(
    private val getMovieListByGenreWithPagingUseCase: GetMovieListByGenreWithPagingUseCase
) : BaseViewModel() {


    private val _movieList2 = MutableStateFlow<PagingData<MovieItem>?>(null)
    val movieList2= _movieList2.asStateFlow()


    fun fetchMoviesWithPaging(genreId: Int){
        viewModelScope.launch {
            getMovieListByGenreWithPagingUseCase
                .getMovieListWithPaging(genreId)
                .collectLatest {
                    _movieList2.value=it
                }

        }
    }
    override fun resetLiveData() {
    }
}