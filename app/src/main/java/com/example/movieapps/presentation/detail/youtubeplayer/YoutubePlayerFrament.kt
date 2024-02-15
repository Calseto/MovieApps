package com.example.movieapps.presentation.detail.youtubeplayer

import android.widget.FrameLayout
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.databinding.FragmentYoutubePlayerBinding

class YoutubePlayerFrament : BaseFragment<FragmentYoutubePlayerBinding>() {
    override fun inflateBinding(): FragmentYoutubePlayerBinding {
        return FragmentYoutubePlayerBinding.inflate(layoutInflater)
    }

    override fun getStatusFramelayout(): Pair<FrameLayout, FrameLayout> {
        return Pair(
            binding.loadingFramelayout,
            binding.errorFramelayout
        )
    }

    override fun setupView() {
        
    }
}