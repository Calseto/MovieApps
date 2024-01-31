package com.example.movieapps.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dompekid.base.BaseViewModel
import com.example.movieapps.data.moviedbapi.GetMovieGenre
import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardFragmentViewModel @Inject constructor(
    private val getMovieGenre: GetMovieGenre
):BaseViewModel() {

    private val _genreList = MutableLiveData<GenreCollection>()
    val genreList:LiveData<GenreCollection> = _genreList

    fun fetcMovieGenre(){
        viewModelScope.launch {
            val response = getMovieGenre.getGenre().collectLatest {
                when(it){
                    UiState.Loading->{
                        _loadingState.postValue(true)
                    }
                    is UiState.Success->{
                        _genreList.postValue(it.data.body())
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
        _genreList.postValue(GenreCollection())
    }
}