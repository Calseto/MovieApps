package com.example.movieapps.presentation.specificmoviecollection

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.adapter.CategoricalMovieAdapter
import com.example.movieapps.adapter.CategoricalPagingMovieAdapter
import com.example.movieapps.databinding.FragmentSpecificMovieCollectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SpecificMovieFragment : BaseFragment<FragmentSpecificMovieCollectionBinding>() {

    private val viewModel: SpecificMovieFragmentViewModel by viewModels()
    override fun inflateBinding(): FragmentSpecificMovieCollectionBinding {
        return FragmentSpecificMovieCollectionBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        binding.textView.setOnClickListener {
            val action=SpecificMovieFragmentDirections.actionSpecificMovieFragmentToDashboardFragment2()
            findNavController().navigate(action)

        }
        getMovieListByGenre()
        observeMovieList()

    }

    private fun getPassedGenreId(): Int? {
        return arguments?.getInt("genreId")
    }

    private fun getMovieListByGenre() {
        val genreId = getPassedGenreId()
        if (genreId != null) {
            viewModel.fetchMoviesWithPaging(genreId)
        }
    }

    private fun observeMovieList() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieList2.collectLatest {
                    if (it != null) {
                        val adapter = CategoricalPagingMovieAdapter()
                        binding.rvMovieList.adapter = adapter
                        adapter.submitData(it)

                    }
                }
            }
        }
    }
}