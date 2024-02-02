package com.example.movieapps.presentation.detail.tabfragment.review

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.adapter.CategoricalPagingMovieAdapter
import com.example.movieapps.adapter.MoviewReviewAdapter
import com.example.movieapps.databinding.ItemReviewLayoutBinding
import com.example.movieapps.presentation.detail.DetailMovieViewodel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReviewFrament(
    private val viewodel: DetailMovieViewodel
):BaseFragment<ItemReviewLayoutBinding>() {
    private lateinit var adapter:MoviewReviewAdapter
    override fun inflateBinding(): ItemReviewLayoutBinding {
        return ItemReviewLayoutBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        createAdapter()
        observeModel()
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