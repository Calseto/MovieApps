package com.example.movieapps.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapps.data.moviedbapi.response.MovieDetailsResponse
import com.example.movieapps.presentation.detail.tabfragment.review.ReviewFrament
import com.example.movieapps.presentation.detail.tabfragment.showmore.ShowMoreFragment

class MovieDetailsTabAdapter (
    fragmentActivity: FragmentActivity,
    private val movieDetailsResponse: MovieDetailsResponse
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ReviewFrament()
            1 -> ShowMoreFragment(movieDetailsResponse)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}