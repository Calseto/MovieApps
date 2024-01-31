package com.example.movieapps.presentation.dashboard

import androidx.fragment.app.viewModels
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.adapter.DashboardMovieAdapter
import com.example.movieapps.data.moviedbapi.response.GenresItem
import com.example.movieapps.databinding.FragmentMovieSelectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment:BaseFragment<FragmentMovieSelectionBinding>() {

    private val viewModel:DashboardFragmentViewModel by viewModels()
    override fun inflateBinding(): FragmentMovieSelectionBinding {
        return FragmentMovieSelectionBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        viewModel.fetcMovieGenre()
        handleUi()
    }

    private fun handleUi(){
        handleAllMovieCollection()
    }
    private fun handleAllMovieCollection(){
        viewModel.genreList.observe(viewLifecycleOwner){
            val genres = it.genres
            if (genres != null){
                setDashboardMovieRv(genres)
            }
            else{

            }
        }

    }

    private fun setDashboardMovieRv(list: List<GenresItem?>){
        binding.rvMovieDashboard.adapter  =DashboardMovieAdapter(list)
    }
}