package com.example.movieapps.presentation.dashboard

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.adapter.DashboardMovieAdapter
import com.example.movieapps.data.moviedbapi.response.GenresItem
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.databinding.FragmentMovieSelectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment:BaseFragment<FragmentMovieSelectionBinding>() {

    private val viewModel:DashboardFragmentViewModel by viewModels()
    private lateinit var adapter: DashboardMovieAdapter
    override fun inflateBinding(): FragmentMovieSelectionBinding {
        return FragmentMovieSelectionBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        handleUi()
    }


    private fun handleUi(){
        handleAllMovieCollection()
    }
    private fun handleAllMovieCollection(){
        getMovieGenre()
        observeGenres()
    }

    private fun observeGenres(){
        viewModel.genreList.observe(viewLifecycleOwner){
            val genres = it.genres
            if (genres != null){
                setDashboardMovieRv(genres)
                getAllMovieByGenre(it.genres)
            }
            else{

            }
            observeAllMovie()
        }
    }

    private fun observeAllMovie(){

        viewModel.movieList.forEachIndexed{index, liveData ->
            liveData.observe(viewLifecycleOwner) {
                updateDashBoardMovieRv(index, it.results)
            }
        }
    }


    private fun getMovieGenre(){
        viewModel.fetcMovieGenre()
    }

    private fun getAllMovieByGenre(list: List<GenresItem?>){
        viewModel.fetchAllMovieByGenre(list)
    }

    private fun setDashboardMovieRv(list: List<GenresItem?>){
        adapter=DashboardMovieAdapter(requireContext(),list){
            val bundle= bundleOf("genreId" to it)
            val action = DashboardFragmentDirections.actionDashboardFragmentToSpecificMovieFragment().actionId
            findNavController().navigate(action,bundle)
        }
        binding.rvMovieDashboard.adapter  = adapter
    }
    private fun updateDashBoardMovieRv(position:Int,list :List<MovieItem?>?){
        adapter.updateMovieRV(position,list)
    }
}