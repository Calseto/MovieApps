package com.example.movieapps.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.dompekid.base.BaseViewModel
import com.example.movieapps.data.moviedbapi.response.MovieDetailsResponse
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.ReviewItem
import com.example.movieapps.data.moviedbapi.usecase.GetMovieDetailsUseCase
import com.example.movieapps.data.moviedbapi.usecase.GetMovieReviewsUseCase
import com.example.movieapps.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewodel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
):BaseViewModel() {

    private val _movieDetails = MutableStateFlow<MovieDetailsResponse?>(null)
    val movieDetails = _movieDetails.asStateFlow()

    private val _movieReviews = MutableStateFlow<PagingData<ReviewItem>?>(null)
    val movieReviews = _movieReviews.asStateFlow()

//    private val _authorsList = MutableLiveData<List<Cast>>()
//    val authorsList: LiveData<List<Cast>> = _authorsList

//    private val _video = MutableLiveData<List<Result>>()
//    val video: LiveData<List<Result>> = _video

    fun fetchMovieReviews(movieId: Int){
        viewModelScope.launch {
            getMovieReviewsUseCase
                .getMovieReviews(movieId)
                .collectLatest {
                    _movieReviews.value=it
                }

        }
    }

    fun fetchMovieDetails(movieId:Int){
        viewModelScope.launch {
            getMovieDetailsUseCase.getMovieDetails(movieId).collectLatest {
                when(it){
                    UiState.Loading->{
                        _loadingState.postValue(true)
                    }
                    is UiState.Success->{
                        _movieDetails.value=it.data.body()
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