package com.example.movieapps.presentation.movieselection

import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.R
import com.example.movieapps.adapter.DashboardMovieAdapter
import com.example.movieapps.data.moviedbapi.response.GenresItem
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.databinding.FragmentMovieSelectionBinding
import com.example.movieapps.presentation.DashBoardFragmentDirections
import com.example.movieapps.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSelectionFragment : BaseFragment<FragmentMovieSelectionBinding>() {

    private val viewModel: MovieSelectionViewModel by viewModels()
    private lateinit var adapter: DashboardMovieAdapter

    override fun inflateBinding(): FragmentMovieSelectionBinding {
        return FragmentMovieSelectionBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        setupRefresh()
        handleUi()
    }

    override fun getStatusFramelayout(): Pair<FrameLayout, FrameLayout> {
        return Pair(
            binding.loadingFramelayout,
            binding.errorFramelayout
        )
    }

    private fun setupRefresh(){
        binding.swipeRefreshLayout2.setOnRefreshListener {
            openLoadingFragment()
            handleUi()
        }
    }


    private fun handleUi() {
        handleAllMovieCollection()
    }

    private fun handleAllMovieCollection() {
        getMovieGenre()
        observeGenres()
    }

    private fun observeGenres() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.genreList.collect {
                    when (it) {
                        UiState.Loading -> {
                            closeErrorFragment()
                            openLoadingFragment()
                        }

                        is UiState.Success -> {
                            val genres = it.data.body()?.genres
                            if (genres != null) {
                                setDashboardMovieRv(genres)
                            }
                            closeLoadingFragment()
                        }

                        is UiState.Error -> {
                            closeLoadingFragment()
                            openErrorFragment(it.message)

                        }
                    }
                }
            }

        }
    }

//    private fun observeAllMovie() {
//        lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.movieList.forEachIndexed { index, stateFlow ->
//                    stateFlow.collectLatest{
//                        when (it) {
//                            UiState.Loading -> {
//                                openLoadingFragment(binding.progresBar)
//                            }
//
//                            is UiState.Success -> {
//                                updateDashBoardMovieRv(index, it.data?.results)
//                            }
//
//                            is UiState.Error -> {
//                                closeLoadingFragment(binding.progresBar)
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//    }
    private fun observeAllMovie() {

        viewModel.movieList.forEachIndexed{index,stateflow->
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle((Lifecycle.State.STARTED)){
                    stateflow.collectLatest{
                        when (it) {
                            UiState.Loading -> {
                                closeErrorFragment()
                                openLoadingFragment()
                            }

                            is UiState.Success -> {
                                closeLoadingFragment()
                                updateDashBoardMovieRv(index, it.data?.results)
                            }

                            is UiState.Error -> {
                                closeLoadingFragment()
                                openErrorFragment(it.message)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getMovieGenre() {
        viewModel.fetcMovieGenre()
    }

    private fun getAllMovieByGenre(list: List<GenresItem?>) {
        viewModel.fetchAllMovieByGenre(list)
    }

    private fun setDashboardMovieRv(list: List<GenresItem?>) {
        adapter = DashboardMovieAdapter(requireContext(), list) { id, name ->
            val bundle = bundleOf("genreId" to id, "genreName" to name)
            val action =
                DashBoardFragmentDirections.actionDashBoardFragmentToSpecificMovieFragment().actionId
            findNavController().navigate(action, bundle)
        }

        binding.rvMovieDashboard.adapter = adapter
        getAllMovieByGenre(list)
        observeAllMovie()
    }
    private fun updateDashBoardMovieRv(position: Int, list: List<MovieItem?>?) {
        adapter.updateMovieRV(position, list)
    }
}