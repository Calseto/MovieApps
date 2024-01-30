package com.example.movieapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.movieapps.databinding.ActivityMovieAppsBinding

class MovieAppsActivity : AppCompatActivity() {
    lateinit var binding:ActivityMovieAppsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_apps)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        findNavController(binding.activityFrameLayout.id)

    }
}