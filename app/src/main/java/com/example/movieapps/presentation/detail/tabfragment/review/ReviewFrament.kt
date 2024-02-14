package com.example.movieapps.presentation.detail.tabfragment.review

import android.util.Log
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.adapter.CategoricalPagingMovieAdapter
import com.example.movieapps.adapter.MoviewReviewAdapter
import com.example.movieapps.databinding.ComponentReviewLayoutBinding
import com.example.movieapps.presentation.detail.DetailMovieViewodel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReviewFrament(
    private val viewodel: DetailMovieViewodel
):BaseFragment<ComponentReviewLayoutBinding>() {
    private lateinit var adapter:MoviewReviewAdapter
    override fun inflateBinding(): ComponentReviewLayoutBinding {
        return ComponentReviewLayoutBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        createAdapter()
        observeModel()
    }

    override fun getStatusFramelayout(): Pair<FrameLayout, FrameLayout> {
        return Pair(
            binding.loadingFramelayout,
            binding.errorFramelayout
        )
    }

    private fun observeModel(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewodel.movieReviews.collectLatest {
                    if (it != null) {
                        Log.d("TAG", "observeModel: ")
                        adapter.submitData(it)

                    }
                }
            }

        }
    }
    private fun createAdapter(){
        adapter = MoviewReviewAdapter()
        binding.rvReviewLayout.adapter=adapter
    }
}