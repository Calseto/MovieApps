package com.example.movieapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.example.movieapps.databinding.ActivityMovieAppsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieAppsActivity : AppCompatActivity() {
    lateinit var binding:ActivityMovieAppsBinding

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("TAG", "onBackPressed: ")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMovieAppsBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        findNavController(binding.activityFragmentFrame.id)

    }
}