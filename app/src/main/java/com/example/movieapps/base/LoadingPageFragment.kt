package com.example.movieapps.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.R
import com.example.movieapps.databinding.FragmentLoadingBinding

class LoadingPageFragment:Fragment() {
    private var _binding :FragmentLoadingBinding? = null
    protected val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentLoadingBinding.inflate(layoutInflater)
        Glide.with(this)
            .asGif()  // Specify that the resource is a GIF
            .load(R.drawable.finish)  // Replace 'your_gif_filename' with the actual name of your GIF file (without the extension)
            .into(binding.loadingImage);
        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}