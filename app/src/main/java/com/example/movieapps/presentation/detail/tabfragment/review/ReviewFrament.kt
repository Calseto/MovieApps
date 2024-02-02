package com.example.movieapps.presentation.detail.tabfragment.review

import com.example.dompekid.base.BaseFragment
import com.example.movieapps.databinding.ItemReviewLayoutBinding

class ReviewFrament(
):BaseFragment<ItemReviewLayoutBinding>() {
    override fun inflateBinding(): ItemReviewLayoutBinding {
        return ItemReviewLayoutBinding.inflate(layoutInflater)
    }

    override fun setupView() {
    }
}