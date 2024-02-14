package com.example.movieapps.presentation.searchmovies

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.dompekid.base.BaseViewModel
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.MovieQueryItem
import com.example.movieapps.data.moviedbapi.usecase.GetMovieQueryResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieFragmentViewModel @Inject constructor(
    private val getMovieQueryResultUseCase: GetMovieQueryResultUseCase
):BaseViewModel() {

    private val _movieList3 = MutableStateFlow<PagingData<MovieQueryItem>?>(null)
    val movieList3= _movieList3.asStateFlow()

    fun fetchMovieQueryResult(query:String){
        viewModelScope.launch {
            getMovieQueryResultUseCase
                .getMovieQueryResult(query)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _movieList3.value=it
                }

        }
    }

    override fun resetLiveData() {

    }
}