package com.example.movieapps.presentation.detail.tabfragment.showmore

import android.util.Log
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.data.moviedbapi.response.MovieDetailsResponse
import com.example.movieapps.data.moviedbapi.response.ProductionCompaniesItem
import com.example.movieapps.databinding.ItemShowMoreLayoutBinding

class ShowMoreFragment (
    private val detail:MovieDetailsResponse
):BaseFragment<ItemShowMoreLayoutBinding>(){
    override fun inflateBinding(): ItemShowMoreLayoutBinding {
        return ItemShowMoreLayoutBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        binding.apply {
            tvBudget.text = "Budget: " + detail.budget.toString()
            tvCompanies.text = "Companies: " + companiesStringParser(detail.productionCompanies)
            tvRuntime.text = "Run Time: " + detail.runtime.toString() + " Minutes"
            tvReleasedDate.text = "Release Date: "+detail.releaseDate
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