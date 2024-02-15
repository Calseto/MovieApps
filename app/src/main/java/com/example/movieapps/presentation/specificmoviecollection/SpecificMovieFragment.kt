package com.example.movieapps.presentation.specificmoviecollection

import android.util.Log
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.adapter.CategoricalPagingMovieAdapter
import com.example.movieapps.databinding.FragmentSpecificMovieCollectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SpecificMovieFragment : BaseFragment<FragmentSpecificMovieCollectionBinding>() {

    private val viewModel: SpecificMovieFragmentViewModel by viewModels()
    private lateinit var adapter:CategoricalPagingMovieAdapter
    override fun inflateBinding(): FragmentSpecificMovieCollectionBinding {
        return FragmentSpecificMovieCollectionBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        setupGenreText()
        createAdapter()
        getMovieListByGenre()
        observeMovieList()

    }
    override fun getStatusFramelayout(): Pair<FrameLayout, FrameLayout> {
        return Pair(
            binding.loadingFramelayout,
            binding.errorFramelayout
        )
    }

    private fun setupGenreText(){
        binding.textView.text=arguments?.getString("genreName")
    }

    private fun createAdapter(){
        adapter = CategoricalPagingMovieAdapter{
            setupNavigationOnItemClick(it)
        }
        binding.rvMovieList.adapter = adapter
    }

    private fun setupNavigationOnItemClick(id:Int){
        val bundle= bundleOf("movieId" to id )
        val action=SpecificMovieFragmentDirections.actionSpecificMovieFragmentToDetailMovieFragment().actionId
        findNavController().navigate(action,bundle)
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
                        adapter.submitData(it)
                    }
                }
            }

        }
    }
}