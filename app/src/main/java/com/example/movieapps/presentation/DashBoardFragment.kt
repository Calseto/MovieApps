package com.example.movieapps.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movieapps.R
import com.example.movieapps.databinding.FragmentDashboardBinding
import com.example.movieapps.presentation.movieselection.MovieSelectionFragment
import com.example.movieapps.presentation.searchmovies.SearchMovieFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashBoardFragment:Fragment(){
    private lateinit var binding : FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentDashboardBinding.inflate(layoutInflater)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener (onNavigationItemSelectedListener)
        if(savedInstanceState==null) binding.bottomNavigationView.selectedItemId=R.id.navigationHome
        return binding.root

    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigationHome-> {
                    replaceFragment(MovieSelectionFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigationSearch-> {
                    replaceFragment(SearchMovieFragment())
                    return@OnNavigationItemSelectedListener true
                }
//                R.id.navigationBookmark-> {
//                    replaceFragment()
//                    return@OnNavigationItemSelectedListener true
//                }
                else->{return@OnNavigationItemSelectedListener true}
            }
        }

    private fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(binding.frameLayout.id,fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}