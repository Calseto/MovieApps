package com.example.movieapps.presentation.searchmovies

import android.widget.FrameLayout
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.adapter.MoviewQueryResultAdapter
import com.example.movieapps.databinding.FragmentSearchMoviesBinding
import com.example.movieapps.presentation.DashBoardFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMovieFragment:BaseFragment<FragmentSearchMoviesBinding>() {

    private val viewModel: SearchMovieFragmentViewModel by viewModels()
    private lateinit var adapter:MoviewQueryResultAdapter
    override fun inflateBinding(): FragmentSearchMoviesBinding {
        return FragmentSearchMoviesBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        createAdapter()
        handleSearch()
        observeMovieList()

    }

    private fun handleSearch(){
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!=null&&newText.length>3){
                    viewModel.fetchMovieQueryResult(newText)
                }
                return true
            }
        })
    }

    private fun observeMovieList() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieList3.collectLatest {
                    if (it != null) {
                        adapter.submitData(it)
                    }
                }
            }

        }
    }

    override fun getStatusFramelayout(): Pair<FrameLayout, FrameLayout> {
        return Pair(binding.loadingFramelayout,binding.errorFramelayout)
    }

    private fun createAdapter(){
        adapter = MoviewQueryResultAdapter{
            setupNavigationOnItemClick(it)
        }
        binding.rvMovieList.adapter = adapter
    }
    private fun setupNavigationOnItemClick(id:Int){
        val bundle= bundleOf("movieId" to id )
        val action= DashBoardFragmentDirections.actionDashBoardFragmentToDetailMovieFragment().actionId
        findNavController().navigate(action,bundle)
    }
}