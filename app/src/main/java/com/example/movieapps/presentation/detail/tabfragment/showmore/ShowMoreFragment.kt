package com.example.movieapps.presentation.detail.tabfragment.showmore

import android.util.Log
import android.webkit.WebChromeClient
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.data.moviedbapi.response.MovieDetailsResponse
import com.example.movieapps.data.moviedbapi.response.ProductionCompaniesItem
import com.example.movieapps.databinding.ItemShowMoreLayoutBinding
import com.example.movieapps.presentation.detail.DetailMovieViewodel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ShowMoreFragment (
    private val viewModel:DetailMovieViewodel
):BaseFragment<ItemShowMoreLayoutBinding>(){
    override fun inflateBinding(): ItemShowMoreLayoutBinding {
        return ItemShowMoreLayoutBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        observeModel()
    }

    private fun observeModel(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetails.collectLatest {
                    if (it != null) {
                        binding.apply {
                            tvBudget.text = "Budget: " + it.budget.toString()
                            tvCompanies.text = "Companies: " + companiesStringParser(it.productionCompanies)
                            tvRuntime.text = "Run Time: " + it.runtime.toString() + " Minutes"
                            tvReleasedDate.text = "Release Date: "+it.releaseDate
                        }
                    }
                }
            }

        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.video.collectLatest {
                    if (it?.results != null) {
                        val webView = binding.webViewLayout.webviewYoutube
                        val movieVideoString =
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${it.results[0]?.key}?\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
                        webView.loadData(movieVideoString, "text/html", "utf-8")
                        webView.settings.javaScriptEnabled = true
                        webView.webChromeClient = WebChromeClient()
                    }
                }
            }

        }
    }

    private fun companiesStringParser(list:List<ProductionCompaniesItem?>?):String{
        var string = ""
        if (list != null) {
            for(companies in list){
                string=string+companies?.name+", "
            }
        }
        return string
    }
}