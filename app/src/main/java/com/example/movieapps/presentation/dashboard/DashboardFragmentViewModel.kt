package com.example.movieapps.presentation.dashboard

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dompekid.base.BaseViewModel
import com.example.movieapps.data.moviedbapi.usecase.GetMovieGenreUseCase
import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.data.moviedbapi.response.GenresItem
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import com.example.movieapps.data.moviedbapi.usecase.GetMovieListByGenreUseCase
import com.example.movieapps.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardFragmentViewModel @Inject constructor(
    private val getMovieGenreUseCase: GetMovieGenreUseCase,
    private val getMovieListByGenreUseCase: GetMovieListByGenreUseCase
):BaseViewModel() {

    private val _genreList = MutableLiveData<GenreCollection>()
    val genreList:LiveData<GenreCollection> = _genreList

    var _movieList : MutableList<MutableLiveData<MovieListReqResponse>> = mutableListOf()
    var movieList: List<LiveData<MovieListReqResponse>> = _movieList



    fun fetcMovieGenre(){
        viewModelScope.launch {
            getMovieGenreUseCase.getGenre().collectLatest {
                when(it){
                    UiState.Loading->{
                        _loadingState.postValue(true)
                    }
                    is UiState.Success->{
                        _genreList.postValue(it.data.body())
                    }
                    is UiState.Error->{
                        _errorMessage.postValue(it.message)
                        _loadingState.postValue(false)
                    }
                }
            }
        }
    }

    fun fetchAllMovieByGenre(listGenre: List<GenresItem?>){
        setUpMovielistLiveData(listGenre)
        viewModelScope.launch {
            _movieList.forEachIndexed{index,mutableLiveData->
                val genreId=listGenre[index]?.id
                if(genreId!=null) {
                    getMovieListByGenreUseCase.getMovieList(genreId).collectLatest {
                        when (it) {
                            UiState.Loading -> {
                                _loadingState.postValue(true)
                            }

                            is UiState.Success -> {
                                mutableLiveData.postValue(it.data.body())

                            }

                            is UiState.Error -> {
                                _errorMessage.postValue(it.message)
                            }
                        }
                    }
                }
                _loadingState.postValue(false)
            }

        }
    }

    private fun setUpMovielistLiveData(list: List<GenresItem?>){
        for (genre in list){
            _movieList.add(MutableLiveData<MovieListReqResponse>())

        }
    }
    override fun resetLiveData() {
        _genreList.postValue(GenreCollection())
        _movieList.clear()
    }
}