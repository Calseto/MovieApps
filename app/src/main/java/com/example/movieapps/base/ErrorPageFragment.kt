package com.example.movieapps.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.databinding.FragmentErrorBinding
import com.example.movieapps.databinding.FragmentLoadingBinding

class ErrorPageFragment():Fragment() {
    private lateinit var binding : FragmentErrorBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentErrorBinding.inflate(layoutInflater)
        return binding.root

    }
    fun setErrorMessage(errorMsg:String){
        binding.errorMessage.text=errorMsg
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}