package com.example.movieapps.presentation.specificmoviecollection

import androidx.lifecycle.viewModelScope
import com.example.dompekid.base.BaseViewModel
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import com.example.movieapps.data.moviedbapi.usecase.GetMovieListByGenreUseCase
import com.example.movieapps.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificMovieFragmentViewModel @Inject constructor(
    private val getMovieListByGenreUseCase: GetMovieListByGenreUseCase
) : BaseViewModel() {

    private val _movieList = MutableStateFlow<MovieListReqResponse?>(MovieListReqResponse())
    val movieList= _movieList.asStateFlow()

    fun fetchMovieListByGenre(genreId:Int){
        viewModelScope.launch {
            getMovieListByGenreUseCase.getMovieList(genreId).collectLatest{
                when(it){
                    UiState.Loading->{
                        _loadingState.postValue(true)
                    }
                    is UiState.Success->{
                        _movieList.value=it.data.body()
                        _loadingState.postValue(false)
                    }
                    is UiState.Error->{
                        _errorMessage.postValue(it.message)
                        _loadingState.postValue(false)
                    }
                }

            }
        }
    }
    override fun resetLiveData() {
    }
}