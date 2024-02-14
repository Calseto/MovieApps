package com.example.movieapps.presentation.movieselection

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.dompekid.base.BaseViewModel
import com.example.movieapps.data.moviedbapi.usecase.GetMovieGenreUseCase
import com.example.movieapps.data.moviedbapi.response.GenreCollection
import com.example.movieapps.data.moviedbapi.response.GenresItem
import com.example.movieapps.data.moviedbapi.response.MovieListReqResponse
import com.example.movieapps.data.moviedbapi.usecase.GetMovieListByGenreUseCase
import com.example.movieapps.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieSelectionViewModel @Inject constructor(
    private val getMovieGenreUseCase: GetMovieGenreUseCase,
    private val getMovieListByGenreUseCase: GetMovieListByGenreUseCase
) : BaseViewModel() {

    private val _genreList = MutableStateFlow<UiState<Response<GenreCollection>>>(UiState.Loading)
    val genreList: StateFlow<UiState<Response<GenreCollection>>> = _genreList

    var _movieList: MutableList<MutableStateFlow<UiState<MovieListReqResponse?>>> = mutableListOf()
    val movieList: List<MutableStateFlow<UiState<MovieListReqResponse?>>> = _movieList


    fun fetcMovieGenre() {
        _genreList.value=UiState.Loading
        viewModelScope.launch {
            getMovieGenreUseCase.getGenre().collectLatest {
                _genreList.value = it
            }
        }
    }

    fun fetchAllMovieByGenre(listGenre: List<GenresItem?>) {
        setUpMovielistLiveData(listGenre)
        viewModelScope.launch {
            for (index in _movieList.indices) {


                val genreId = listGenre[index]?.id ?: continue

                getMovieListByGenreUseCase.getMovieList(genreId).collectLatest {
                    when (it) {
                        UiState.Loading -> {
                            _movieList[index].value = UiState.Loading
                        }
                        is UiState.Success -> {
                            _movieList[index].value =  UiState.Success(it.data.body())
                        }
                        is UiState.Error -> {
                            _movieList[index].value = UiState.Error(it.message)
                        }
                    }
                }

            }

        }
    }

    fun setUpMovielistLiveData(list: List<GenresItem?>) {
        _movieList.clear()
        for (genre in list) {
            _movieList.add(MutableStateFlow(UiState.Loading))

        }
    }

    override fun resetLiveData() {
        _genreList.value = UiState.Loading
        _movieList.clear()
    }
}