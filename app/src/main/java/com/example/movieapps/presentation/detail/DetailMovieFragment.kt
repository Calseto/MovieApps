package com.example.movieapps.presentation.detail

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.adapter.MovieDetailsTabAdapter
import com.example.movieapps.data.moviedbapi.response.GenresItem
import com.example.movieapps.data.moviedbapi.response.MovieDetailsResponse
import com.example.movieapps.databinding.FragmentDetailMovieBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMovieFragment:BaseFragment<FragmentDetailMovieBinding>() {

    private val viewModel:DetailMovieViewodel by viewModels()
    override fun inflateBinding(): FragmentDetailMovieBinding {
        return FragmentDetailMovieBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        getMovieDetail()
        observeViewModel()
    }
    private fun getMovieDetail(){
        val id:Int? = arguments?.getInt("movieId")
        if (id!=null) {
            viewModel.fetchMovieDetails(id)
        }

    }
    private fun observeViewModel(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetails.collectLatest {
                    if (it != null) {
                        setupTabLayout(it)
                        setupViewData(it)
                    }
                }
            }

        }
    }

    private fun setupViewData(data:MovieDetailsResponse){
        binding.apply {
            Glide.with(requireActivity())
                .load("https://image.tmdb.org/t/p/w500" + data.posterPath)
                .centerCrop()
                .into(ivMovieCover)
            Glide.with(requireActivity())
                .load("https://image.tmdb.org/t/p/w500" + data.backdropPath)
                .centerCrop()
                .into(ivBackDrop)
            tvDescription.text=data.overview
            tvGenre.text=parseGenrese(data.genres)
            tvTitle.text=data.title

        }
    }

    private fun parseGenrese(list:List<GenresItem?>?):String{
        var string:String=""
        if (list != null) {
            for (item in list){
                string=string + item?.name+", "
            }
        }
        return string
    }

    private fun setupTabLayout(movieDetail:MovieDetailsResponse){
        val viewPager: ViewPager2 = binding.detailsComponentBottom.viewPagerWallet
        val tabLayout: TabLayout = binding.detailsComponentBottom.tabNavWallet
        val adapter = MovieDetailsTabAdapter(requireActivity(), movieDetail)
        viewPager.adapter = adapter

        // Connect TabLayout with ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text="Review"
                1 -> tab.text="More Detail"
            }
        }.attach()
    }
}